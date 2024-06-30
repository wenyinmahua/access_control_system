package com.tyut.accesscontrol.exception;

import com.tyut.accesscontrol.common.BaseResponse;
import com.tyut.accesscontrol.common.ErrorCode;
import com.tyut.accesscontrol.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

	@ExceptionHandler(BusinessException.class)//只去捕获BusinessException的异常
	public BaseResponse businessExceptionHandle(BusinessException e){
		log.error("businessException" +  e.getMessage(),e);
		return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
	}
	@ExceptionHandler(RuntimeException.class)
	public BaseResponse runtimeExceptionHandle(RuntimeException e){
		log.error("runtimeException="+e);
		return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");

	}
}