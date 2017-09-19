package com.hanbit.there.api.exception;

public class ExceptionVO {
	
	private int errorCode;
	private String message;
	public ExceptionVO() {

	}

	public ExceptionVO(String message) {
		this(0, message);
	}

	public ExceptionVO(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
