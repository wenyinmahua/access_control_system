package com.tyut.accesscontrol.controller;

import com.tyut.accesscontrol.common.BaseResponse;
import com.tyut.accesscontrol.common.ResultUtils;
import com.tyut.accesscontrol.model.dto.SignFromDTO;
import com.tyut.accesscontrol.service.AccessService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private AccessService accessService;

	@PostMapping("/signInOrOut")
	public BaseResponse<Boolean> userSignInOrOut(@RequestBody SignFromDTO signFromDTO){
		return ResultUtils.success(accessService.userSignInOrOut(signFromDTO));
	}

}
