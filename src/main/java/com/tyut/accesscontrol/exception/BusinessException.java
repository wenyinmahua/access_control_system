package com.tyut.accesscontrol.exception;


import com.tyut.accesscontrol.common.ErrorCode;

/**
 * 自定义业务异常类
 *
 * mahua
 */
public class BusinessException extends RuntimeException {

	private final int code;
	private String description;

	public BusinessException(int code, String msg, String description) {
		super(msg);
		this.code = code;
		this.description = description;
	}

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
	}

	public BusinessException(ErrorCode errorCode, String description) {
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
