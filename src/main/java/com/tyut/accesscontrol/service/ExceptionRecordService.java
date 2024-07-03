package com.tyut.accesscontrol.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.accesscontrol.common.DeleteRequest;
import com.tyut.accesscontrol.model.dto.ExceptionRecordQueryDTO;
import com.tyut.accesscontrol.model.entity.ExceptionRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author mahua
* @description 针对表【exception_record(异常记录表)】的数据库操作Service
* @createDate 2024-07-03 12:21:26
*/
public interface ExceptionRecordService extends IService<ExceptionRecord> {

	Page<ExceptionRecord> getPageExceptionRecord(ExceptionRecordQueryDTO exceptionRecordQueryDTO);

	Boolean updateExceptionRecord(ExceptionRecord exceptionRecord);

	Boolean deleteExceptionRecord(DeleteRequest deleteRequest);
}
