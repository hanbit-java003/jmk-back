package com.hanbit.there.api.exception;


public class JmkException extends RuntimeException {
	
	private int errorCode = 500;
	
	public JmkException(String message) {
		// 여기서 super는 Runtime Exceptiont이다.
		super(message);
	}
	
	public JmkException(int errorCode, String message) {
		this(message);

		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
