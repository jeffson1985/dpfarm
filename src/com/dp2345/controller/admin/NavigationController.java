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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.Message;
import com.dp2345.Pageable;
import com.dp2345.entity.Navigation;
import com.dp2345.entity.Navigation.Position;
import com.dp2345.service.ArticleCategoryService;
import com.dp2345.service.NavigationService;
import com.dp2345.service.ProductCategoryService;

/**
 * Controller - 导航
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminNavigationController")
@RequestMapping("/admin/navigation")
public class NavigationController extends BaseController {

	@Resource(name = "navigationServiceImpl")
	private NavigationService navigationService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("positions", Position.values());
		model.addAttribute("articleCategoryTree", articleCategoryService.findTree());
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		return "/admin/navigation/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Navigation navigation, RedirectAttributes redirectAttributes) {
		if (!isValid(navigation)) {
			return ERROR_VIEW;
		}
		navigationService.save(navigation);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("positions", Position.values());
		model.addAttribute("articleCategoryTree", articleCategoryService.findTree());
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("navigation", navigationService.find(id));
		return "/admin/navigation/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Navigation navigation, RedirectAttributes redirectAttributes) {
		if (!isValid(navigation)) {
			return ERROR_VIEW;
		}
		navigationService.update(navigation);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("topNavigations", navigationService.findList(Position.top));
		model.addAttribute("middleNavigations", navigationService.findList(Position.middle));
		model.addAttribute("bottomNavigations", navigationService.findList(Position.bottom));
		return "/admin/navigation/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		navigationService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}