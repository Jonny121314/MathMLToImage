package com.hongrant.www.achieve.comm.exception;


public class BusinessException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -620129600748507167L;
	
	protected String code = "-2";
	protected String msg = "业务错误";
	protected String errorType = "ERROR";

	public BusinessException() {
	}

	public BusinessException(String code, String message) {
		super(message);
		this.code = code;
		this.msg = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	
}
