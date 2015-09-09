package com.dp2345.exception;

/**
 * 短信解析异常
 * @author Jeffson
 *
 */
public class SmsParseException extends SmsException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3198019085120281628L;

	public SmsParseException(String msg) {
		super(msg);
	}
	
	/**
	 * Constructor for SMSParseException.
	 * @param msg the detail message
	 * @param cause the root cause from the sms API in use
	 */
	public SmsParseException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Constructor for SmsParseException.
	 * @param cause the root cause from the sms API in use
	 */
	public SmsParseException(Throwable cause) {
		super("Could not parse sms", cause);
	}

}
