package com.tyut.accesscontrol;

import com.tyut.accesscontrol.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class AccessControlSystemApplicationTests {

	@Resource
	private UserService userService;
	@Test
	void contextLoads() {
	}

	@Test
	void getUserIds(){
		List<Long> userIds = userService.getUserIds();
		System.out.println(userIds);
	}

}
