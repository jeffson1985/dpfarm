/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.Message;
import com.dp2345.Pageable;
import com.dp2345.entity.Brand;
import com.dp2345.entity.Brand.Type;
import com.dp2345.service.BrandService;

/**
 * Controller - 品牌
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminBrandController")
@RequestMapping("/admin/brand")
public class BrandController extends BaseController {

	@Resource(name = "brandServiceImpl")
	private BrandService brandService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("types", Type.values());
		return "/admin/brand/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Brand brand, RedirectAttributes redirectAttributes) {
		if (!isValid(brand)) {
			return ERROR_VIEW;
		}
		if (brand.getType() == Type.text) {
			brand.setLogo(null);
		} else if (StringUtils.isEmpty(brand.getLogo())) {
			return ERROR_VIEW;
		}
		brand.setProducts(null);
		brand.setProductCategories(null);
		brand.setPromotions(null);
		brandService.save(brand);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("types", Type.values());
		model.addAttribute("brand", brandService.find(id));
		return "/admin/brand/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Brand brand, RedirectAttributes redirectAttributes) {
		if (!isValid(brand)) {
			return ERROR_VIEW;
		}
		if (brand.getType() == Type.text) {
			brand.setLogo(null);
		} else if (StringUtils.isEmpty(brand.getLogo())) {
			return ERROR_VIEW;
		}
		brandService.update(brand, "products", "productCategories", "promotions");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", brandService.findPage(pageable));
		return "/admin/brand/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		brandService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}