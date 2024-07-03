package com.tyut.accesscontrol.service.impl;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.model.dto.DateQueryDTO;
import com.tyut.accesscontrol.model.entity.Log;
import com.tyut.accesscontrol.model.vo.LogVO;
import com.tyut.accesscontrol.service.LogService;
import com.tyut.accesscontrol.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author mahua
* @description 针对表【log(日志表)】的数据库操作Service实现
* @createDate 2024-07-03 12:21:12
*/
@Service
@Slf4j
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
    implements LogService{

	@Resource
	private LogMapper logMapper;

	@Override
	public LogVO getLogByMonth(DateQueryDTO dateQueryDTO) {
		Date thisDay = dateQueryDTO.getThisDay();
		if (thisDay == null){
			thisDay = new Date();
		}
		LocalDate localDate = thisDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		log.error(localDate.toString());
		List<Log> logs = logMapper.getLogByMonth(localDate);
		long normalAccessNum = 0;
		long abnormalNum = 0;
		Map<LocalDate, Long> checkInNum = new LinkedHashMap<>();
		Map<LocalDate,Long> checkOutNum = new LinkedHashMap<>();
		for (Log log : logs){
			normalAccessNum += log.getTotalCheckedIn() + log.getTotalCheckedOut();
			abnormalNum += log.getTotalRecognitionFailures();
			checkInNum.put(log.getLogDate(),log.getTotalCheckedIn());
			checkOutNum.put(log.getLogDate(),log.getTotalCheckedOut());
		}
		LogVO logVO = new LogVO();
		logVO.setNormalAccessNum(normalAccessNum);
		logVO.setAbnormalNum(abnormalNum);
		logVO.setCheckInNum(checkInNum);
		logVO.setCheckOutNum(checkOutNum);
		return logVO;
	}
}




