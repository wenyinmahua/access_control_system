package com.tyut.accesscontrol.service;

import com.tyut.accesscontrol.model.dto.AdminLoginDTO;
import com.tyut.accesscontrol.model.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author mahua
* @description 针对表【admin(管理员表)】的数据库操作Service
* @createDate 2024-07-01 00:14:15
*/
public interface AdminService extends IService<Admin> {

	Boolean login(AdminLoginDTO adminLoginDTO, HttpServletRequest request);
}
