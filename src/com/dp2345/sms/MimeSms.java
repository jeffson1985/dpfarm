package com.dp2345.sms;

public class MimeSms {
	private SmsConfig config;
	private SimpleSmsMessage simpleMessage;
	public MimeSms(){}
	
	public SmsConfig getConfig() {
		return config;
	}
	
	
	public void setConfig(SmsConfig config) {
		this.config = config;
	}
	public SimpleSmsMessage getSimpleMessage() {
		return simpleMessage;
	}
	public void setSimpleMessage(SimpleSmsMessage simpleMessage) {
		this.simpleMessage = simpleMessage;
	};
	
}
