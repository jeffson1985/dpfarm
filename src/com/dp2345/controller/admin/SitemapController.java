/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.Template;
import com.dp2345.service.StaticService;
import com.dp2345.service.TemplateService;

/**
 * Controller - Sitemap
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminSitemapController")
@RequestMapping("/admin/sitemap")
public class SitemapController extends BaseController {

	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	/**
	 * 生成Sitemap
	 */
	@RequestMapping(value = "/build", method = RequestMethod.GET)
	public String build(ModelMap model) {
		Template sitemapIndexTemplate = templateService.get("sitemapIndex");
		model.addAttribute("sitemapIndexPath", sitemapIndexTemplate.getStaticPath());
		return "/admin/sitemap/build";
	}

	/**
	 * 生成Sitemap
	 */
	@RequestMapping(value = "/build", method = RequestMethod.POST)
	public String build(RedirectAttributes redirectAttributes) {
		staticService.buildSitemap();
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:build.jhtml";
	}

}