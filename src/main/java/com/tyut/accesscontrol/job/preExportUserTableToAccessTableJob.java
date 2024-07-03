package com.tyut.accesscontrol.job;

import com.tyut.accesscontrol.model.entity.Access;
import com.tyut.accesscontrol.service.AccessService;
import com.tyut.accesscontrol.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class preExportUserTableToAccessTableJob {

	@Resource
	private UserService userService;

	@Resource
	private AccessService accessService;



	@Scheduled(cron = "0 0 0 * * ?")
	public void doExportUserTableToAccessTableJob(){
		List<Long> ids = userService.getUserIds();
		if (ids.isEmpty()){
			return;
		}
		for (Long id : ids) {
			Access access = new Access();
			access.setUserId(id);
			accessService.save(access);
		}

	}


}
