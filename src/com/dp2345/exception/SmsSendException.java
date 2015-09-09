package com.dp2345.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.ObjectUtils;

/**
 * 短信发送异常
 * @author Jeffson
 *
 */
public class SmsSendException extends SmsException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7351255609170703339L;

	private transient final Map<Object, Exception> failedMessages;
	private Exception[] messageExceptions;

	/**
	 * Constructor for SmsSendException.
	 * 
	 * @param msg
	 *            the detail message
	 */
	public SmsSendException(String msg) {
		this(msg, null);
	}

	/**
	 * Constructor for SmsSendException.
	 * 
	 * @param msg
	 *            the detail message
	 * @param cause
	 *            the root cause from the mail API in use
	 */
	public SmsSendException(String msg, Throwable cause) {
		super(msg, cause);
		this.failedMessages = new LinkedHashMap<Object, Exception>();
	}

	/**
	 * Constructor for registration of failed messages, with the messages that
	 * failed as keys, and the thrown exceptions as values.
	 * <p>
	 * The messages should be the same that were originally passed to the
	 * invoked send method.
	 * 
	 * @param msg
	 *            the detail message
	 * @param cause
	 *            the root cause from the mail API in use
	 * @param failedMessages
	 *            Map of failed messages as keys and thrown exceptions as values
	 */
	public SmsSendException(String msg, Throwable cause,
			Map<Object, Exception> failedMessages) {
		
		super(msg, cause);
		
		this.failedMessages = new LinkedHashMap<Object, Exception>(failedMessages);
		this.messageExceptions = failedMessages.values()
								.toArray(new Exception[failedMessages.size()]);
	}

	/**
	 * Constructor for registration of failed messages, with the messages that
	 * failed as keys, and the thrown exceptions as values.
	 * <p>
	 * The messages should be the same that were originally passed to the
	 * invoked send method.
	 * 
	 * @param failedMessages
	 *            Map of failed messages as keys and thrown exceptions as values
	 */
	public SmsSendException(Map<Object, Exception> failedMessages) {
		this(null, null, failedMessages);
	}

	/**
	 * Return a Map with the failed messages as keys, and the thrown exceptions
	 * as values.
	 * <p>
	 * Note that a general mail server connection failure will not result in
	 * failed messages being returned here: A message will only be contained
	 * here if actually sending it was attempted but failed.
	 * <p>
	 * The messages will be the same that were originally passed to the invoked
	 * send method, that is, SimpleSmsMessages in case of using the generic
	 * SmsSender interface.
	 * <p>
	 * In case of sending MimeMessage instances via JavaSmsSender, the messages
	 * will be of type MimeMessage.
	 * <p>
	 * <b>NOTE:</b> This Map will not be available after serialization. Use
	 * {@link #getMessageExceptions()} in such a scenario, which will be
	 * available after serialization as well.
	 * 
	 * @return the Map of failed messages as keys and thrown exceptions as
	 *         values
	 * @see SimpleSmsMessage
	 * @see javax.mail.internet.MimeMessage
	 */
	public final Map<Object, Exception> getFailedMessages() {
		return this.failedMessages;
	}

	/**
	 * Return an array with thrown message exceptions.
	 * <p>
	 * Note that a general mail server connection failure will not result in
	 * failed messages being returned here: A message will only be contained
	 * here if actually sending it was attempted but failed.
	 * 
	 * @return the array of thrown message exceptions, or an empty array if no
	 *         failed messages
	 */
	public final Exception[] getMessageExceptions() {
		return (this.messageExceptions != null ? this.messageExceptions: new Exception[0]);
	}

	@Override
	public String getMessage() {
		if (ObjectUtils.isEmpty(this.messageExceptions)) {
			return super.getMessage();
		} else {
			StringBuilder sb = new StringBuilder();
			String baseMessage = super.getMessage();
			if (baseMessage != null) {
				sb.append(baseMessage).append(". ");
			}
			sb.append("Failed messages: ");
			for (int i = 0; i < this.messageExceptions.length; i++) {
				Exception subEx = this.messageExceptions[i];
				sb.append(subEx.toString());
				if (i < this.messageExceptions.length - 1) {
					sb.append("; ");
				}
			}
			return sb.toString();
		}
	}

	@Override
	public String toString() {
		if (ObjectUtils.isEmpty(this.messageExceptions)) {
			return super.toString();
		} else {
			StringBuilder sb = new StringBuilder(super.toString());
			sb.append("; message exceptions (")
					.append(this.messageExceptions.length).append(") are:");
			for (int i = 0; i < this.messageExceptions.length; i++) {
				Exception subEx = this.messageExceptions[i];
				sb.append('\n').append("Failed message ").append(i + 1)
						.append(": ");
				sb.append(subEx);
			}
			return sb.toString();
		}
	}

	@Override
	public void printStackTrace(PrintStream ps) {
		if (ObjectUtils.isEmpty(this.messageExceptions)) {
			super.printStackTrace(ps);
		} else {
			ps.println(super.toString() + "; message exception details ("
					+ this.messageExceptions.length + ") are:");
			for (int i = 0; i < this.messageExceptions.length; i++) {
				Exception subEx = this.messageExceptions[i];
				ps.println("Failed message " + (i + 1) + ":");
				subEx.printStackTrace(ps);
			}
		}
	}

	@Override
	public void printStackTrace(PrintWriter pw) {
		if (ObjectUtils.isEmpty(this.messageExceptions)) {
			super.printStackTrace(pw);
		} else {
			pw.println(super.toString() + "; message exception details ("
					+ this.messageExceptions.length + ") are:");
			for (int i = 0; i < this.messageExceptions.length; i++) {
				Exception subEx = this.messageExceptions[i];
				pw.println("Failed message " + (i + 1) + ":");
				subEx.printStackTrace(pw);
			}
		}
	}
}