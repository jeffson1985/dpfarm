/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.dp2345.Setting;
import com.dp2345.entity.ProductNotify;
import com.dp2345.entity.SafeKey;
import com.dp2345.service.SmsService;
import com.dp2345.service.TemplateService;
import com.dp2345.sms.JavaSmsSenderImpl;
import com.dp2345.sms.MimeSms;
import com.dp2345.sms.SimpleSmsMessage;
import com.dp2345.sms.SmsConfig;
import com.dp2345.util.SettingUtils;
import com.dp2345.util.SpringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Service - 短信
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("smsServiceImpl")
public class SmsServiceImpl implements SmsService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;
	

	/**
	 * 添加邮件发送任务
	 * 
	 * @param mimeMessage
	 *            MimeMessage
	 */
	private void addSendTask(final MimeSms mimeSms) {
		try {
			taskExecutor.execute(new Runnable() {
				public void run() {
					JavaSmsSenderImpl.getInstance(mimeSms.getConfig()).send(mimeSms.getSimpleMessage());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String smsApiHost, String smsApiKey, String smsAccount, String smsPassword, String toSmsPhone, String subject, String templatePath, Map<String, Object> model, boolean async) {
		Assert.hasText(smsApiHost);
		Assert.hasText(smsApiKey);
		Assert.hasText(smsAccount);
		Assert.hasText(smsPassword);
		Assert.hasText(toSmsPhone);
		Assert.hasText(subject);
		Assert.hasText(templatePath);
		try {
			//Setting setting = SettingUtils.get();
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			SmsConfig config = new SmsConfig();
			
			config.setSmsApiHost(smsApiHost);
			config.setSmsApiKey(smsApiKey);
			config.setSmsAccount(smsAccount);
			config.setSmsPassword(smsPassword);
			MimeSms mimeSms = new MimeSms();
			
			mimeSms.setConfig(config);
			SimpleSmsMessage simpleMessage = new SimpleSmsMessage();
			simpleMessage.setSentDate(new Date());
			simpleMessage.setSubject(subject);
			simpleMessage.setText(text);
			simpleMessage.setTo(toSmsPhone);
			mimeSms.setSimpleMessage(simpleMessage);
			if (async) {
				addSendTask(mimeSms);
			} else {
				JavaSmsSenderImpl.getInstance(config).send(simpleMessage);
			}
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String toSmsPhone, String subject, String templatePath, Map<String, Object> model, boolean async) {
		Setting setting = SettingUtils.get();
		send(setting.getSmsApiHost(), setting.getSmsApiKey(), setting.getSmsAccount(), setting.getSmsPassword(), toSmsPhone, subject, templatePath, model, async);
	}

	public void send(String toSmsPhone, String subject, String templatePath, Map<String, Object> model) {
		Setting setting = SettingUtils.get();
		send(setting.getSmsApiHost(), setting.getSmsApiKey(), setting.getSmsAccount(), setting.getSmsPassword(), toSmsPhone, subject, templatePath, model, true);
	}

	public void send(String toSmsPhone, String subject, String templatePath) {
		Setting setting = SettingUtils.get();
		send(setting.getSmsApiHost(), setting.getSmsApiKey(), setting.getSmsAccount(), setting.getSmsPassword(), toSmsPhone, subject, templatePath, null, true);
	}

	public void sendTestSms(String smsApiHost, String smsApiKey, String smsAccount, String smsPassword, String toSmsPhone) {
		Setting setting = SettingUtils.get();
		String subject = SpringUtils.getMessage("admin.setting.testSmsSubject", setting.getSiteName());
		send(toSmsPhone, subject, false);
	}

	public void sendFindPasswordSms(String toSmsPhone, String username, SafeKey safeKey) {
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", username);
		model.put("safeKey", safeKey);
		String subject = SpringUtils.getMessage("shop.password.mailSubject", setting.getSiteName());
		com.dp2345.Template findPasswordSmsTemplate = templateService.get("findPasswordSms");
		send(toSmsPhone, subject, findPasswordSmsTemplate.getTemplatePath(), model);
	}

	public void sendProductNotifySms(ProductNotify productNotify) {
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productNotify", productNotify);
		String subject = SpringUtils.getMessage("admin.productNotify.mailSubject", setting.getSiteName());
		com.dp2345.Template productNotifySmsTemplate = templateService.get("productNotifySms");
		send(productNotify.getEmail(), subject, productNotifySmsTemplate.getTemplatePath(), model);
	}

	// 简单类型短线发送 （同步）
	public void send(String toSmsPhone, String content) {
		send(toSmsPhone, content, false);	
	}

	// 简单类型短线发送（异步）
	public void send(String toSmsPhone, String content, boolean async) {
		Setting setting = SettingUtils.get();
		SmsConfig config = new SmsConfig();
		
		config.setSmsApiHost(setting.getSmsApiHost());
		config.setSmsApiKey(setting.getSmsApiKey());
		config.setSmsAccount(setting.getSmsAccount());
		config.setSmsPassword(setting.getSmsPassword());
		MimeSms mimeSms = new MimeSms();
		
		mimeSms.setConfig(config);
		SimpleSmsMessage simpleMessage = new SimpleSmsMessage();
		simpleMessage.setSentDate(new Date());
		simpleMessage.setSubject("");
		simpleMessage.setText(content);
		simpleMessage.setTo(toSmsPhone);
		mimeSms.setSimpleMessage(simpleMessage);
		if (async) {
			addSendTask(mimeSms);
		} else {
			JavaSmsSenderImpl.getInstance(config).send(simpleMessage);
		}
		
	}

}