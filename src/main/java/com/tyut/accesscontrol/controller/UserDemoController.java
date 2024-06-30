package com.tyut.accesscontrol.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.tyut.accesscontrol.common.BaseResponse;
import com.tyut.accesscontrol.common.DeleteRequest;
import com.tyut.accesscontrol.common.ErrorCode;
import com.tyut.accesscontrol.common.ResultUtils;
import com.tyut.accesscontrol.exception.BusinessException;
import com.tyut.accesscontrol.model.dto.*;
import com.tyut.accesscontrol.model.entity.UserDemo;
import com.tyut.accesscontrol.model.vo.UserVO;
import com.tyut.accesscontrol.service.UserDemoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserDemoController {

    @Resource
    private UserDemoService userDemoService;

    // region 登录相关

    /**
     * 用户注册
     *
     * @param userDemoRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserDemoRegisterRequest userDemoRegisterRequest) {
        if (userDemoRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userDemoRegisterRequest.getUserAccount();
        String userPassword = userDemoRegisterRequest.getUserPassword();
        String checkPassword = userDemoRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userDemoService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userDemoLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserDemoLoginRequest userDemoLoginRequest, HttpServletRequest request) {
        if (userDemoLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userDemoLoginRequest.getUserAccount();
        String userPassword = userDemoLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserDemo userDemo = userDemoService.userLogin(userAccount, userPassword, request);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDemo,userVO);
        return ResultUtils.success(userVO);
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userDemoService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {
        UserDemo userDemo = userDemoService.getLoginUser(request);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDemo, userVO);
        return ResultUtils.success(userVO);
    }

    // endregion

    // region 增删改查

    /**
     * 创建用户
     *
     * @param userDemoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addUser(@RequestBody UserDemoAddRequest userDemoAddRequest, HttpServletRequest request) {
        if (userDemoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserDemo userDemo = new UserDemo();
        BeanUtils.copyProperties(userDemoAddRequest, userDemo);
        boolean result = userDemoService.save(userDemo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(userDemo.getId());
    }

    /**
     * 删除用户
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userDemoService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新用户
     *
     * @param userDemoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody UserDemoUpdateRequest userDemoUpdateRequest, HttpServletRequest request) {
        if (userDemoUpdateRequest == null || userDemoUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserDemo userDemo = new UserDemo();
        BeanUtils.copyProperties(userDemoUpdateRequest, userDemo);
        boolean result = userDemoService.updateById(userDemo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取用户
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<UserVO> getUserById(int id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserDemo userDemo = userDemoService.getById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDemo, userVO);
        return ResultUtils.success(userVO);
    }

    /**
     * 获取用户列表
     *
     * @param userDemoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<UserVO>> listUser(UserDemoQueryRequest userDemoQueryRequest, HttpServletRequest request) {
        UserDemo userDemoQuery = new UserDemo();
        if (userDemoQueryRequest != null) {
            BeanUtils.copyProperties(userDemoQueryRequest, userDemoQuery);
        }
        QueryWrapper<UserDemo> queryWrapper = new QueryWrapper<>(userDemoQuery);
        List<UserDemo> userDemoList = userDemoService.list(queryWrapper);
        List<UserVO> userVOList = userDemoList.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(userVOList);
    }

    /**
     * 分页获取用户列表
     *
     * @param userDemoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<UserVO>> listUserByPage(UserDemoQueryRequest userDemoQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        UserDemo userDemoQuery = new UserDemo();
        if (userDemoQueryRequest != null) {
            BeanUtils.copyProperties(userDemoQueryRequest, userDemoQuery);
            current = userDemoQueryRequest.getCurrent();
            size = userDemoQueryRequest.getPageSize();
        }
        QueryWrapper<UserDemo> queryWrapper = new QueryWrapper<>(userDemoQuery);
        Page<UserDemo> userPage = userDemoService.page(new Page<>(current, size), queryWrapper);
        Page<UserVO> userVOPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<UserVO> userVOList = userPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }

    // endregion
}