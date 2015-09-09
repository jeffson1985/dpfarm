package com.dp2345.sms;

import java.util.Date;

import com.dp2345.exception.SmsParseException;


public interface SmsMessage {
	
	public void setTo(String to) throws SmsParseException;

	public void setSentDate(Date sentDate) throws SmsParseException;

	public void setSubject(String subject) throws SmsParseException;

	public void setText(String text) throws SmsParseException;

}
