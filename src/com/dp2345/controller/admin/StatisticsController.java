/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.Setting;
import com.dp2345.service.CacheService;
import com.dp2345.util.SettingUtils;

/**
 * Controller - 统计
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("statisticsController")
@RequestMapping("/admin/statistics")
public class StatisticsController extends BaseController {

	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view() {
		return "/admin/statistics/view";
	}

	/**
	 * 设置
	 */
	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public String setting() {
		return "/admin/statistics/setting";
	}

	/**
	 * 设置
	 */
	@RequestMapping(value = "/setting", method = RequestMethod.POST)
	public String setting(@RequestParam(defaultValue = "false") Boolean isEnabled, RedirectAttributes redirectAttributes) {
		Setting setting = SettingUtils.get();
		if (isEnabled) {
			if (StringUtils.isEmpty(setting.getCnzzSiteId()) || StringUtils.isEmpty(setting.getCnzzPassword())) {
				try {
					String createAccountUrl = "http://intf.cnzz.com/user/companion/dp2345.php?domain=" + setting.getSiteUrl() + "&key=" + DigestUtils.md5Hex(setting.getSiteUrl() + "Lfg4uP0H");
					URLConnection urlConnection = new URL(createAccountUrl).openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					String line = null;
					while ((line = in.readLine()) != null) {
						if (line.contains("@")) {
							break;
						}
					}
					if (line != null) {
						setting.setCnzzSiteId(StringUtils.substringBefore(line, "@"));
						setting.setCnzzPassword(StringUtils.substringAfter(line, "@"));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		setting.setIsCnzzEnabled(isEnabled);
		SettingUtils.set(setting);
		cacheService.clear();
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:setting.jhtml";
	}

}