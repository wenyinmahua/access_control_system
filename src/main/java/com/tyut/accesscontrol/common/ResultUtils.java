package com.tyut.accesscontrol.common;

/**
 * 返回工具类
 *
 * @author mahua
 */
public class ResultUtils {


	public static <T> BaseResponse<T> success(T data) {
		return new BaseResponse<>( 0, data, "操作成功");
	}

	/**
	 * 成功
	 * @param msg
	 * @return
	 */
	public static <T> BaseResponse<T> success(T data,String description) {
		return new BaseResponse( 0, data, description);
	}


	public static <T> BaseResponse<T> error(ErrorCode errorCode) {
		return new BaseResponse<>(errorCode);
	}
	public static BaseResponse error(ErrorCode errorCode, String description) {
		return new BaseResponse(errorCode.getCode(),null, errorCode.getMessage(), description);
	}

	public static BaseResponse error(int code, String message,String description) {
		return new BaseResponse(code, null, message, description);
	}
	public static BaseResponse error(ErrorCode errorCode, String message, String description) {
		return new BaseResponse(errorCode.getCode(), null, message, description);
	}

}