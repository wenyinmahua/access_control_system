package com.tyut.accesscontrol.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.common.ErrorCode;
import com.tyut.accesscontrol.constant.UserConstant;
import com.tyut.accesscontrol.exception.BusinessException;
import com.tyut.accesscontrol.model.dto.AdminLoginDTO;
import com.tyut.accesscontrol.model.dto.AdminRegisterDTO;
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

	private static final String SALT = "TYUT";

	/**
	 * 管理员登录
	 * @param adminLoginDTO
	 * @param request
	 * @return
	 */
	@Override
	public Admin login(AdminLoginDTO adminLoginDTO, HttpServletRequest request) {
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
		Admin admin = this.getOne(queryWrapper);  //获取管理员对象
		if (admin == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
		}
		String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());  //密码MD5加密，并拼接SALT字符串拼接到用户密码前
		if (!admin.getUserPassword().equals(encryptPassword)){
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码错误");
		}
		admin.setUserPassword(null);
		request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, admin);//但是这个键不唯一啊
		return admin;
	}

	/**
	 * 管理员登出
	 * @param request
	 * @return
	 */
	@Override
	public Boolean logout(HttpServletRequest request) {
		if (request == null){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR);
		}
		request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
		return true;
	}

	/**
	 * 获取已登录用户
	 * @param request
	 * @return
	 */
	@Override
	public Admin getLoginAdmin(HttpServletRequest request) {
		if (request == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		Admin admin = (Admin) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
		Admin loginAdmin = this.getById(admin.getId());
		loginAdmin.setUserPassword(null);
		return loginAdmin;
	}

	/**
	 * 管理员注册
	 * @param adminRegisterDTO
	 * @return
	 */
	@Override
	public Boolean register(AdminRegisterDTO adminRegisterDTO) {
		if (adminRegisterDTO == null){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR);
		}
		String userAccount = adminRegisterDTO.getUserAccount();
		String userPassword = adminRegisterDTO.getUserPassword();
		String checkPassword = adminRegisterDTO.getCheckPassword();
		if (StringUtils.isAnyEmpty(userAccount,userPassword, checkPassword)){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		if (!userPassword.equals(checkPassword)){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"两次输入的密码不一致");
		}
		Admin admin = new Admin();
		admin.setUserAccount(userAccount);
		admin.setUserPassword(DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes()));  //MD5加密
		return this.save(admin);
	}

	/**
	 * 更新管理员信息
	 * @param admin
	 * @return
	 */
	@Override
	public Boolean updateAdmin(Admin admin) {
		if (admin == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",admin.getId());
		return this.update(admin,queryWrapper);
	}
}




