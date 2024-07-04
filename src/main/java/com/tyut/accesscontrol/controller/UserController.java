package com.tyut.accesscontrol.controller;

import com.tyut.accesscontrol.common.BaseResponse;
import com.tyut.accesscontrol.common.ResultUtils;
import com.tyut.accesscontrol.model.dto.UserDTO;
import com.tyut.accesscontrol.model.entity.User;
import com.tyut.accesscontrol.service.AccessService;
import com.tyut.accesscontrol.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private AccessService accessService;

	@Resource
	private UserService userService;

	@PostMapping("/signInOrOut")
	public BaseResponse<Boolean> userSignInOrOut(@RequestBody MultipartFile file) throws IOException {
		return accessService.userSignInOrOut(file);
	}

	/**
	 * 用户注册
	 * @param userDTO
	 * @return id
	 */
	@PostMapping("/register")
	public BaseResponse<Long> userRegister(@RequestBody UserDTO userDTO){
		return ResultUtils.success(userService.userRegister(userDTO));
	}


}
