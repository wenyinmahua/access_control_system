package com.tyut.accesscontrol.controller;

import com.tyut.accesscontrol.common.BaseResponse;
import com.tyut.accesscontrol.common.ResultUtils;
import com.tyut.accesscontrol.service.AccessService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private AccessService accessService;

	@PostMapping("/signInOrOut")
	public BaseResponse<Boolean> userSignInOrOut(@RequestBody MultipartFile file) throws IOException {
		return accessService.userSignInOrOut(file);
	}


}
