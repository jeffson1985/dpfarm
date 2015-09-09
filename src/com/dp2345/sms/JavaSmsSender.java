package com.dp2345.sms;

import com.dp2345.exception.SmsException;


public interface JavaSmsSender {
	
	void send(SimpleSmsMessage simpleMessage) throws SmsException;

	
	void send(SimpleSmsMessage[] simpleMessages) throws SmsException;


}
