/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.CommonAttributes;
import com.dp2345.FileInfo.FileType;
import com.dp2345.Message;
import com.dp2345.Setting;
import com.dp2345.Setting.AccountLockType;
import com.dp2345.Setting.CaptchaType;
import com.dp2345.Setting.ConsultationAuthority;
import com.dp2345.Setting.ReviewAuthority;
import com.dp2345.Setting.RoundType;
import com.dp2345.Setting.StockAllocationTime;
import com.dp2345.Setting.WatermarkPosition;
import com.dp2345.exception.SmsSendException;
import com.dp2345.service.CacheService;
import com.dp2345.service.FileService;
import com.dp2345.service.MailService;
import com.dp2345.service.SmsService;
import com.dp2345.service.StaticService;
import com.dp2345.util.SettingUtils;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPSenderFailedException;

/**
 * Controller - 系统设置
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminstingController")
@RequestMapping("/admin/setting")
public class SettingController extends BaseController {

	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;

	/**
	 * 邮件测试
	 */
	@RequestMapping(value = "/mail_test", method = RequestMethod.POST)
	public @ResponseBody Message mailTest(String smtpFromMail, String smtpHost,
			Integer smtpPort, String smtpUsername, String smtpPassword,
			String toMail) {
		if (StringUtils.isEmpty(toMail)) {
			return ERROR_MESSAGE;
		}
		Setting setting = SettingUtils.get();
		if (StringUtils.isEmpty(smtpPassword)) {
			smtpPassword = setting.getSmtpPassword();
		}
		try {
			if (!isValid(Setting.class, "smtpFromMail", smtpFromMail)
					|| !isValid(Setting.class, "smtpHost", smtpHost)
					|| !isValid(Setting.class, "smtpPort", smtpPort)
					|| !isValid(Setting.class, "smtpUsername", smtpUsername)) {
				return ERROR_MESSAGE;
			}
			mailService.sendTestMail(smtpFromMail, smtpHost, smtpPort,
					smtpUsername, smtpPassword, toMail);
		} catch (MailSendException e) {
			Exception[] messageExceptions = e.getMessageExceptions();
			if (messageExceptions != null) {
				for (Exception exception : messageExceptions) {
					if (exception instanceof SMTPSendFailedException) {
						SMTPSendFailedException smtpSendFailedException = (SMTPSendFailedException) exception;
						Exception nextException = smtpSendFailedException
								.getNextException();
						if (nextException instanceof SMTPSenderFailedException) {
							return Message
									.error("admin.setting.mailTestSenderFailed");
						}
					} else if (exception instanceof MessagingException) {
						MessagingException messagingException = (MessagingException) exception;
						Exception nextException = messagingException
								.getNextException();
						if (nextException instanceof UnknownHostException) {
							return Message
									.error("admin.setting.mailTestUnknownHost");
						} else if (nextException instanceof ConnectException) {
							return Message
									.error("admin.setting.mailTestConnect");
						}
					}
				}
			}
			return Message.error("admin.setting.mailTestError");
		} catch (MailAuthenticationException e) {
			return Message.error("admin.setting.mailTestAuthentication");
		} catch (Exception e) {
			return Message.error("admin.setting.mailTestError");
		}
		return Message.success("admin.setting.mailTestSuccess");
	}

	/**
	 * 短信测试
	 */
	@RequestMapping(value = "/sms_test", method = RequestMethod.POST)
	public @ResponseBody Message smsTest(String smsApiHost, String smsApiKey,
			String smsAccount, String smsPassword, String toSmsPhone) {
		if (StringUtils.isEmpty(toSmsPhone)) {
			return ERROR_MESSAGE;
		}
		Setting setting = SettingUtils.get();
		if (StringUtils.isEmpty(smsPassword)) {
			smsPassword = setting.getSmsPassword();
		}
		try {
			if (!isValid(Setting.class, "smsApiHost", smsApiHost)
					|| !isValid(Setting.class, "smsApiKey", smsApiKey)
					|| !isValid(Setting.class, "smsAccount", smsAccount)) {
				return ERROR_MESSAGE;
			}
			smsService.sendTestSms(smsApiHost, smsApiKey, smsAccount,
					smsPassword, toSmsPhone);
		} catch (SmsSendException e) {
			int code = Integer.parseInt(e.getMessage());
			if (code > 0) {
				// 调用API时发生错误，需要开发者进行相应的处理。
				return Message.error("admin.setting.mailTestSenderFailed");
			}
			if (code > -50 && code <= -1) {
				// 权限验证失败，需要开发者进行相应的处理。
				return Message.error("admin.setting.mailTestUnknownHost");
			}
			if (code <= -50) {
				// 系统内部错误，请联系技术支持，调查问题原因并获得解决方案。
				return Message.error("admin.setting.mailTestConnect");
			}

			return Message.error("admin.setting.mailTestError");
		}
		return Message.success("admin.setting.mailTestSuccess");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(ModelMap model) {
		model.addAttribute("watermarkPositions", WatermarkPosition.values());
		model.addAttribute("roundTypes", RoundType.values());
		model.addAttribute("captchaTypes", CaptchaType.values());
		model.addAttribute("accountLockTypes", AccountLockType.values());
		model.addAttribute("stockAllocationTimes", StockAllocationTime.values());
		model.addAttribute("reviewAuthorities", ReviewAuthority.values());
		model.addAttribute("consultationAuthorities",
				ConsultationAuthority.values());
		return "/admin/setting/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Setting setting, MultipartFile watermarkImageFile,
			RedirectAttributes redirectAttributes) {
		if (!isValid(setting)) {
			return ERROR_VIEW;
		}
		if (setting.getUsernameMinLength() > setting.getUsernameMaxLength()
				|| setting.getPasswordMinLength() > setting
						.getPasswordMinLength()) {
			return ERROR_VIEW;
		}
		Setting srcSetting = SettingUtils.get();
		if (StringUtils.isEmpty(setting.getSmtpPassword())) {
			setting.setSmtpPassword(srcSetting.getSmtpPassword());
		}
		if (watermarkImageFile != null && !watermarkImageFile.isEmpty()) {
			if (!fileService.isValid(FileType.image, watermarkImageFile)) {
				addFlashMessage(redirectAttributes,
						Message.error("admin.upload.invalid"));
				return "redirect:edit.jhtml";
			}
			String watermarkImage = fileService.uploadLocal(FileType.image,
					watermarkImageFile);
			setting.setWatermarkImage(watermarkImage);
		} else {
			setting.setWatermarkImage(srcSetting.getWatermarkImage());
		}
		setting.setCnzzSiteId(srcSetting.getCnzzSiteId());
		setting.setCnzzPassword(srcSetting.getCnzzPassword());
		SettingUtils.set(setting);
		cacheService.clear();
		staticService.buildIndex();
		staticService.buildOther();

		OutputStream outputStream = null;
		try {
			org.springframework.core.io.Resource resource = new ClassPathResource(
					CommonAttributes.DP2345_PROPERTIES_PATH);
			Properties properties = PropertiesLoaderUtils
					.loadProperties(resource);
			String templateUpdateDelay = properties
					.getProperty("template.update_delay");
			String messageCacheSeconds = properties
					.getProperty("message.cache_seconds");
			if (setting.getIsDevelopmentEnabled()) {
				if (!templateUpdateDelay.equals("0")
						|| !messageCacheSeconds.equals("0")) {
					outputStream = new FileOutputStream(resource.getFile());
					properties.setProperty("template.update_delay", "0");
					properties.setProperty("message.cache_seconds", "0");
					properties.store(outputStream, "DP2345 PROPERTIES");
				}
			} else {
				if (templateUpdateDelay.equals("0")
						|| messageCacheSeconds.equals("0")) {
					outputStream = new FileOutputStream(resource.getFile());
					properties.setProperty("template.update_delay", "3600");
					properties.setProperty("message.cache_seconds", "3600");
					properties.store(outputStream, "DP2345 PROPERTIES");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:edit.jhtml";
	}

}