package com.dp2345.sms;
/**
 * 手机短信配置类
 * @author Sun
 *
 */
public class SmsConfig {
	
	private SmsIsp isp;
	private String smsApiHost;
	private String smsApiKey;
	private String smsAccount;
	private String smsPassword;
	
	public SmsConfig(){}
	
	public String getSmsApiHost() {
		return smsApiHost;
	}
	public void setSmsApiHost(String smsApiHost) {
		this.smsApiHost = smsApiHost;
	}
	public String getSmsApiKey() {
		return smsApiKey;
	}
	public void setSmsApiKey(String smsApiKey) {
		this.smsApiKey = smsApiKey;
	}
	public String getSmsAccount() {
		return smsAccount;
	}
	public void setSmsAccount(String smsAccount) {
		this.smsAccount = smsAccount;
	}
	public String getSmsPassword() {
		return smsPassword;
	}
	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public SmsIsp getIsp() {
		return isp;
	}

	public void setIsp(SmsIsp isp) {
		this.isp = isp;
	}
	
}
