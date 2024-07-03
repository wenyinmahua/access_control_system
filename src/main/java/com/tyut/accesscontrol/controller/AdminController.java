package com.tyut.accesscontrol.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.accesscontrol.common.BaseResponse;
import com.tyut.accesscontrol.common.DeleteRequest;
import com.tyut.accesscontrol.common.ResultUtils;
import com.tyut.accesscontrol.model.dto.AccessQueryDTO;
import com.tyut.accesscontrol.model.dto.AdminLoginDTO;
import com.tyut.accesscontrol.model.dto.AdminRegisterDTO;
import com.tyut.accesscontrol.model.dto.UserQueryDTO;
import com.tyut.accesscontrol.model.entity.Access;
import com.tyut.accesscontrol.model.entity.Admin;
import com.tyut.accesscontrol.model.entity.User;
import com.tyut.accesscontrol.service.AccessService;
import com.tyut.accesscontrol.service.AdminService;
import com.tyut.accesscontrol.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Resource
	private AdminService adminService;

	@Resource
	private UserService userService;

	@Resource
	private AccessService accessService;

	// region 管理员crud
	// 管理员登录
	@PostMapping("/login")
	@ApiOperation("管理员登录")
	public BaseResponse<Admin> adminLogin(@RequestBody AdminLoginDTO adminLoginDTO, HttpServletRequest request){
		return ResultUtils.success(adminService.login(adminLoginDTO, request));
	}
	// 管理员注册
	@ApiOperation("管理员注册")
	@PostMapping("/register")
	public BaseResponse<Boolean> adminRegister(@RequestBody AdminRegisterDTO adminRegisterDTO){
		return ResultUtils.success(adminService.register(adminRegisterDTO));
	}

	// 管理员修改个人信息
	@PostMapping("/update/admin")
	@ApiOperation("管理员修改个人信息")
	public BaseResponse<Boolean> updateAdmin(@RequestBody Admin admin){
		return ResultUtils.success(adminService.updateAdmin(admin));
	}


	// 管理员退出登录
	@GetMapping("/logout")
	@ApiOperation("管理员退出登录")
	public BaseResponse<Boolean> logout(HttpServletRequest request){
		return ResultUtils.success(adminService.logout(request));
	}

	// 得到当前登录的管理员
	@GetMapping("/get/loginAdmin")
	@ApiOperation("获得当前登录的管理员")
	public BaseResponse<Admin> getLoginAdmin(HttpServletRequest request){
		return ResultUtils.success(adminService.getLoginAdmin(request));
	}
	// endregion

	// region 用户crud

	// 暂无增加某个用户的需求

	// 分页查询所有的用户
	@PostMapping("/user/page")
	@ApiOperation("分页获取用户")
	public BaseResponse<Page<User>> getPageUser(UserQueryDTO userQueryDTO){
		return ResultUtils.success(userService.getPageUser(userQueryDTO));
	}

	// 修改某个用户
	@PostMapping("/update/user")
	@ApiOperation("修好某个用户的个人信息")
	public BaseResponse<Boolean> updateUser(@RequestBody User user){
		return ResultUtils.success(userService.updateUser(user));
	}

	// 删除某个用户
	@PostMapping("/delete/user")
	@ApiOperation("删除某个用户")
	public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest){
		return ResultUtils.success(userService.deleteUserById(deleteRequest));
	}


	// endregion


	// region 出入表增删改查

	//获取出入表
	@PostMapping("/access/page")
	@ApiOperation("分页获取出入表")
	public BaseResponse<Page<Access>> getPageAccess(@RequestBody AccessQueryDTO accessQueryDTO){
		return ResultUtils.success(accessService.getPageAccess(accessQueryDTO));
	}

	// 修改出入表
	@PostMapping("/access/update")
	@ApiOperation("修改出入表中的数据")
	public BaseResponse<Boolean> updateAccess(@RequestBody Access access){
		return ResultUtils.success(accessService.updateAccess(access));
	}

	// 删除出入表
	@PostMapping("/access/delete")
	@ApiOperation("删除出入表中的数据")
	public BaseResponse<Boolean> deleteAccessById(@RequestBody DeleteRequest deleteRequest){
		return ResultUtils.success(accessService.deleteAccessById(deleteRequest));
	}

	//endregion

}
