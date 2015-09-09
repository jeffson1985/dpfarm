package com.dp2345.service;

import java.util.Map;

import com.dp2345.entity.SafeKey;

public interface SmsService {
	
	void send(String toSmsPhone, String content);
	void send(String toSmsPhone, String content, boolean async);

	/**
	 * 发送短信
	 * 
	 * @param smsApiHost
	 *            SMS服务器地址
	 * @param smsApiKey
	 *            SMS服务器验证秘钥
	 * @param smsAccount
	 *            SMS用户名
	 * @param smsPassword
	 *            SMS密码
	 * @param toSmsPhone
	 *            收件人电话
	 * @param subject
	 *            主题
	 * @param templatePath
	 *            模板路径
	 * @param model
	 *            数据
	 * @param async
	 *            是否异步
	 */
	void send(String smsApiHost, String smsApiKey, String smsAccount, String smsPassword, String toSmsPhone, String subject, String templatePath, Map<String, Object> model, boolean async);

	/**
	 * 发送短信
	 * 
	 * @param toSmsPhone
	 *            收件人电话
	 * @param subject
	 *            主题
	 * @param templatePath
	 *            模板路径
	 * @param model
	 *            数据
	 * @param async
	 *            是否异步
	 */
	void send(String toSmsPhone, String subject, String templatePath, Map<String, Object> model, boolean async);

	/**
	 * 发送短信(异步)
	 * 
	 * @param toSmsPhone
	 *            收件人电话
	 * @param subject
	 *            主题
	 * @param templatePath
	 *            模板路径
	 * @param model
	 *            数据
	 */
	void send(String toSmsPhone, String subject, String templatePath, Map<String, Object> model);

	/**
	 * 发送短信(异步)
	 * 
	 * @param toSms
	 *            收件人电话
	 * @param subject
	 *            主题
	 * @param templatePath
	 *            模板路径
	 */
	void send(String toSmsPhone, String subject, String templatePath);

	/**
	 * 发送测试短信
	 * 
	 * @param smsApiHost
	 *            SMS服务器地址
	 * @param smsApiKey
	 *            SMS服务器验证秘钥
	 * @param smsAccount
	 *            SMS用户名
	 * @param smsPassword
	 *            SMS密码
	 * @param toSmsPhone
	 *            收件人电话
	 */
	void sendTestSms(String smsApiHost, String smsApiKey, String smsAccount, String smsPassword, String toSmsPhone);

	/**
	 * 发送找回密码短信
	 * 
	 * @param toSmsPhone
	 *            收件人电话
	 * @param username
	 *            用户名
	 * @param safeKey
	 *            安全密匙
	 */
	void sendFindPasswordSms(String toSmsPhone, String username, SafeKey safeKey);

	/**
	 * 发送到货通知短信
	 * 
	 * @param productNotify
	 *            到货通知
	 */
	
}
