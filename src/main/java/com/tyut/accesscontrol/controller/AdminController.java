package com.tyut.accesscontrol.controller;

import com.tyut.accesscontrol.common.BaseResponse;
import com.tyut.accesscontrol.common.ResultUtils;
import com.tyut.accesscontrol.model.dto.AdminLoginDTO;
import com.tyut.accesscontrol.model.entity.Admin;
import com.tyut.accesscontrol.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Resource
	private AdminService adminService;

	@PostMapping("/login")
	public BaseResponse<Boolean> adminLogin(@RequestBody AdminLoginDTO adminLoginDTO, HttpServletRequest request){
		return ResultUtils.success(adminService.login(adminLoginDTO, request));
	}
}
