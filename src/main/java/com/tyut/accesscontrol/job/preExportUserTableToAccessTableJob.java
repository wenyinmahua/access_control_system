package com.tyut.accesscontrol.job;

import com.tyut.accesscontrol.model.entity.Access;
import com.tyut.accesscontrol.model.entity.Log;
import com.tyut.accesscontrol.service.AccessService;
import com.tyut.accesscontrol.service.LogService;
import com.tyut.accesscontrol.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class preExportUserTableToAccessTableJob {

	@Resource
	private UserService userService;

	@Resource
	private AccessService accessService;

	@Resource
	private LogService logService;



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

	@Scheduled(cron = "0 54 13 * * ?")
	public void doUpdateLogJob(){
		Log log = new Log();
		log.setLogDate(LocalDate.now());
		logService.save(log);
	}

}
