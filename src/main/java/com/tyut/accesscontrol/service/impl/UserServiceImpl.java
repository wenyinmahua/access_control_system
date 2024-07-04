package com.tyut.accesscontrol.service.impl;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.common.DeleteRequest;
import com.tyut.accesscontrol.common.ErrorCode;
import com.tyut.accesscontrol.exception.BusinessException;
import com.tyut.accesscontrol.model.dto.UserDTO;
import com.tyut.accesscontrol.model.dto.UserQueryDTO;
import com.tyut.accesscontrol.model.entity.User;
import com.tyut.accesscontrol.service.UserService;
import com.tyut.accesscontrol.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author mahua
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-07-01 00:14:42
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

	@Resource
	private UserMapper userMapper;

	@Override
	public Page<User> getPageUser(UserQueryDTO userQueryDTO) {
		if (userQueryDTO == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String username = userQueryDTO.getUsername();
		Integer gender = userQueryDTO.getGender();
		Integer age = userQueryDTO.getAge();
		String position = userQueryDTO.getPosition();
		long current = userQueryDTO.getCurrent();
		long pageSize = userQueryDTO.getPageSize();
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(username)){
			queryWrapper.like("username",username);
		}
		if (gender != null){
			queryWrapper.eq("gender",gender);
		}
		if (age != null){
			queryWrapper.eq("age",age);
		}
		if (StringUtils.isNotEmpty(position)){
			queryWrapper.like("position",position);
		}
		return this.page(new Page<>(current, pageSize), queryWrapper);
	}

	@Override
	public Boolean updateUser(User user) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",user.getId());
		return this.update(user,queryWrapper);
	}

	@Override
	public Boolean deleteUserById(DeleteRequest deleteRequest) {
		if (deleteRequest == null){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR);
		}
		Long id = deleteRequest.getId();
		if (id < 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		return this.removeById(id);
	}

	@Override
	public List<Long> getUserIds() {
		return userMapper.getUserIds();
	}

	@Override
	public List<Long> getUserIdsByName(String username) {
		List<Long> ids = userMapper.getUserIdsByName(username);
		return ids;
	}

	@Override
	public String getUserNameById(Long userId) {
		return userMapper.getUserNameById(userId);
	}

    @Override
    public Long userRegister(UserDTO userDTO) {
		String username = userDTO.getUsername();
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isEmpty(username)){
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名不能为空");
		}
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		this.save(user);
		Long id = user.getId();
		return id;
    }
}




