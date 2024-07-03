package com.tyut.accesscontrol.service;

import com.tyut.accesscontrol.model.dto.DateQueryDTO;
import com.tyut.accesscontrol.model.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tyut.accesscontrol.model.vo.LogVO;

/**
* @author mahua
* @description 针对表【log(日志表)】的数据库操作Service
* @createDate 2024-07-03 12:21:12
*/
public interface LogService extends IService<Log> {

	LogVO getLogByMonth(DateQueryDTO dateQueryDTO);
}
