package com.dp2345.sms;

import java.io.Serializable;
import java.util.Date;

import org.springframework.mail.MailMessage;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.dp2345.exception.SmsParseException;

public class SimpleSmsMessage implements SmsMessage, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7179703099926867645L;

	private String to;

	private Date sentDate;

	private String subject;

	private String text;
	
	public SimpleSmsMessage(){}

	public void setTo(String to) throws SmsParseException {
		this.to = to;

	}

	public void setSentDate(Date sentDate) throws SmsParseException {

		this.sentDate = sentDate;
	}

	public void setSubject(String subject) throws SmsParseException {

		this.subject = subject;
	}

	public void setText(String text) throws SmsParseException {

		this.text = text;
	}

	public String getTo() {
		return to;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}

	public void copyTo(MailMessage target) {
		Assert.notNull(target, "The 'target' message argument cannot be null");

		if (getTo() != null) {
			target.setTo(getTo());
		}

		if (getSentDate() != null) {
			target.setSentDate(getSentDate());
		}
		if (getSubject() != null) {
			target.setSubject(getSubject());
		}
		if (getText() != null) {
			target.setText(getText());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SimpleSmsMessage: ");
		sb.append("to=").append(this.to).append("; ");
		sb.append("sentDate=").append(this.sentDate).append("; ");
		sb.append("subject=").append(this.subject).append("; ");
		sb.append("text=").append(this.text);
		return sb.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SimpleSmsMessage)) {
			return false;
		}
		SimpleSmsMessage otherMessage = (SimpleSmsMessage) other;
		return (this.to.equals(otherMessage.to)
				&& ObjectUtils.nullSafeEquals(this.sentDate,
						otherMessage.sentDate)
				&& ObjectUtils.nullSafeEquals(this.subject,
						otherMessage.subject) && ObjectUtils.nullSafeEquals(
				this.text, otherMessage.text));
	}

	@Override
	public int hashCode() {
		int hashCode = (this.to == null ? 0 : this.to.hashCode());
		hashCode = 29 * hashCode
				+ (this.sentDate == null ? 0 : this.sentDate.hashCode());
		hashCode = 29 * hashCode
				+ (this.subject == null ? 0 : this.subject.hashCode());
		hashCode = 29 * hashCode
				+ (this.text == null ? 0 : this.text.hashCode());
		return hashCode;
	}
}
