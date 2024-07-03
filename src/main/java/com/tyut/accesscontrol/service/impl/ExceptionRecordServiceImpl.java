package com.tyut.accesscontrol.service.impl;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.common.DeleteRequest;
import com.tyut.accesscontrol.common.ErrorCode;
import com.tyut.accesscontrol.exception.BusinessException;
import com.tyut.accesscontrol.model.dto.ExceptionRecordQueryDTO;
import com.tyut.accesscontrol.model.entity.ExceptionRecord;
import com.tyut.accesscontrol.service.ExceptionRecordService;
import com.tyut.accesscontrol.mapper.ExceptionRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author mahua
* @description 针对表【exception_record(异常记录表)】的数据库操作Service实现
* @createDate 2024-07-03 12:21:26
*/
@Service
@Slf4j
public class ExceptionRecordServiceImpl extends ServiceImpl<ExceptionRecordMapper, ExceptionRecord>
    implements ExceptionRecordService{

	@Override
	public Page<ExceptionRecord> getPageExceptionRecord(ExceptionRecordQueryDTO exceptionRecordQueryDTO) {
		Long id = exceptionRecordQueryDTO.getId();
		Date recognitionTime = exceptionRecordQueryDTO.getRecognitionTime();
		Integer isAlarm = exceptionRecordQueryDTO.getIsAlarm();
		long current = exceptionRecordQueryDTO.getCurrent();
		long pageSize = exceptionRecordQueryDTO.getPageSize();
		QueryWrapper<ExceptionRecord> queryWrapper = new QueryWrapper<>();
		if (id != null){
			queryWrapper.eq("id",id);
		}
		if (recognitionTime != null){
			// 获得 recognitionTime 一天之后的日期
			LocalDate localDate = recognitionTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
			LocalDate nextDay = localDate.plus(1, ChronoUnit.DAYS);
			queryWrapper.ge("recognitionTime",recognitionTime);
			log.error(nextDay.toString());
			queryWrapper.lt("recognitionTime",nextDay);
		}
		if (isAlarm != null){
			queryWrapper.eq("isAlarm",isAlarm);
		}
		return this.page(new Page<>(current, pageSize), queryWrapper);
	}

	@Override
	public Boolean updateExceptionRecord(ExceptionRecord exceptionRecord) {
     Long id = exceptionRecord.getId();
	 return this.update(exceptionRecord,new QueryWrapper<ExceptionRecord>().eq("id",id));
	}

	@Override
	public Boolean deleteExceptionRecord(DeleteRequest deleteRequest) {
		if (deleteRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		return this.removeById(deleteRequest.getId());
	}
}




