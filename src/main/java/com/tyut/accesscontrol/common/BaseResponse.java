package com.tyut.accesscontrol.common;

import lombok.Data;

@Data
public class BaseResponse<T> {   //实现 Serializable 接口,用于数据传输

	private int code;

	private T data;

	private String message;

	private String description;

	public BaseResponse(int code, T data,String message,String description) {
		this.code = code;
		this.data = data;
		this.message = message;
		this.description = description;
	}



	public BaseResponse(int code, T data, String message) {
		this(code, data, message,"");
	}

	public BaseResponse(ErrorCode errorCode){
		this(errorCode.getCode(),null,errorCode.getMessage());
	}

}
