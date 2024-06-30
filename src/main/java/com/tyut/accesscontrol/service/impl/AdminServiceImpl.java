package com.tyut.accesscontrol.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.common.ErrorCode;
import com.tyut.accesscontrol.constant.UserConstant;
import com.tyut.accesscontrol.exception.BusinessException;
import com.tyut.accesscontrol.model.dto.AdminLoginDTO;
import com.tyut.accesscontrol.model.entity.Admin;
import com.tyut.accesscontrol.service.AdminService;
import com.tyut.accesscontrol.mapper.AdminMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
* @author mahua
* @description 针对表【admin(管理员表)】的数据库操作Service实现
* @createDate 2024-07-01 00:14:15
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

	private final String SALT = "TYUT";

	@Override
	public Boolean login(AdminLoginDTO adminLoginDTO, HttpServletRequest request) {
		if (adminLoginDTO == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String userAccount = adminLoginDTO.getUserAccount();
		String userPassword = adminLoginDTO.getUserPassword();
		if (StringUtils.isAnyEmpty(userAccount, userPassword)){
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号密码不能为空");
		}
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userAccount",userAccount);
		Admin admin = this.getOne(queryWrapper);
		if (admin == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
		}
		String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
		if (!admin.getUserPassword().equals(encryptPassword)){
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码错误");
		}
		request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, admin);
		return true;
	}
}




