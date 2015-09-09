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
import com.dp2345.entity.Tag;
import com.dp2345.entity.BaseEntity.Save;
import com.dp2345.entity.Tag.Type;
import com.dp2345.service.TagService;

/**
 * Controller - 标签
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminTagController")
@RequestMapping("/admin/tag")
public class TagController extends BaseController {

	@Resource(name = "tagServiceImpl")
	private TagService tagService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("types", Type.values());
		return "/admin/tag/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Tag tag, RedirectAttributes redirectAttributes) {
		if (!isValid(tag, Save.class)) {
			return ERROR_VIEW;
		}
		tag.setArticles(null);
		tag.setProducts(null);
		tagService.save(tag);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("types", Type.values());
		model.addAttribute("tag", tagService.find(id));
		return "/admin/tag/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Tag tag, RedirectAttributes redirectAttributes) {
		if (!isValid(tag)) {
			return ERROR_VIEW;
		}
		tagService.update(tag, "type", "articles", "products");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", tagService.findPage(pageable));
		return "/admin/tag/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		tagService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}