package com.startuplab.common.exception;

public class MyException extends Exception {
	private MyError err;
	private String custom;

	public MyException(MyError err) {
		this.err = err;
	}

	public MyException(MyError err, String msg) {
		this.err = err;
		this.custom = msg;
	}

	public MyException(String msg) {
		this.err = MyError.CUSTOM;
		this.custom = msg;
	}

	public static int successCode = 0;
	public static int systemErrorCode = 9999;
	public static String systemErrorMsg = "SYSTEM_EXCEPTION";

	public static enum MyError {
		CUSTOM, SYSTEM_EXCEPTION, SUCCESS, INVALID_FORMAT, INVALID_PARAMS, NO_REQUIRED_PARAMS, MAXIMUM_UPLOAD_SIZE_EXCEED
	}

	public String getMsg() {
		return getMsg(null);
	}

	public String getMsg(MyError error) {
		if (error == null)
			error = this.err;
		String returnValue = systemErrorMsg;
		switch (error) {
			case CUSTOM:
				returnValue = this.custom;
				break;
			case SUCCESS:
				returnValue = "SUCCESS";
				break;
			case INVALID_FORMAT:
				returnValue = "INVALID_FORMAT";
				break;
			case INVALID_PARAMS:
				returnValue = "INVALID_PARAMS";
				break;
			case NO_REQUIRED_PARAMS:
				returnValue = "NO_REQUIRED_PARAMS";
				break;
			case MAXIMUM_UPLOAD_SIZE_EXCEED:
				returnValue = "MAXIMUM_UPLOAD_SIZE_EXCEED";
				break;
			case SYSTEM_EXCEPTION:
				returnValue = "SYSTEM_EXCEPTION";
				break;
		}
		return returnValue;
	}

	public int getCode() {
		int returnValue = systemErrorCode;
		switch (err) {
			case SUCCESS:
				returnValue = successCode;
				break;
			case SYSTEM_EXCEPTION:
				returnValue = systemErrorCode;
				break;
			case INVALID_FORMAT:
				returnValue = 9998;
				break;
			case INVALID_PARAMS:
				returnValue = 9997;
				break;
			case NO_REQUIRED_PARAMS:
				returnValue = 9996;
				break;
			case MAXIMUM_UPLOAD_SIZE_EXCEED:
				returnValue = 9995;
				break;
			case CUSTOM:
				returnValue = 9900;
				break;
		}
		return returnValue;
	}

	public MyError getMyError() {
		return this.err;
	}

	@Override
	public void printStackTrace() {}
}
