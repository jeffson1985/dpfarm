package com.dp2345.exception;

import org.springframework.core.NestedRuntimeException;
/**
 * 短信异常抽象类
 * @author Sun
 *
 */
public abstract class SmsException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2071641536371906992L;

	/**
	 * Constructor for SmsException.
	 * @param msg the detail message
	 */
	public SmsException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for SmsException.
	 * @param msg the detail message
	 * @param cause the root cause from the sms API in use
	 */
	public SmsException(String msg, Throwable cause) {
		super(msg, cause);
	}

}