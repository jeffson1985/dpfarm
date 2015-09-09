/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.Message;
import com.dp2345.entity.Shop;
import com.dp2345.entity.ShopCategory;
import com.dp2345.service.ShopCategoryService;

/**
 * Controller - 店铺分类
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminShopCategoryController")
@RequestMapping("/admin/shop_category")
public class ShopCategoryController extends BaseController {

	@Resource(name = "shopCategoryServiceImpl")
	private ShopCategoryService shopCategoryService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("shopCategoryTree", shopCategoryService.findTree());
		return "/admin/shop_category/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ShopCategory shopCategory, Long parentId, RedirectAttributes redirectAttributes) {
		shopCategory.setParent(shopCategoryService.find(parentId));
		if (!isValid(shopCategory)) {
			return ERROR_VIEW;
		}
		shopCategory.setTreePath(null);
		shopCategory.setGrade(null);
		shopCategory.setChildren(null);
		shopCategory.setShops(null);
		shopCategoryService.save(shopCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		ShopCategory shopCategory = shopCategoryService.find(id);
		model.addAttribute("shopCategoryTree", shopCategoryService.findTree());
		model.addAttribute("shopCategory", shopCategory);
		model.addAttribute("children", shopCategoryService.findChildren(shopCategory));
		return "/admin/shop_category/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ShopCategory shopCategory, Long parentId, RedirectAttributes redirectAttributes) {
		shopCategory.setParent(shopCategoryService.find(parentId));
		if (!isValid(shopCategory)) {
			return ERROR_VIEW;
		}
		if (shopCategory.getParent() != null) {
			ShopCategory parent = shopCategory.getParent();
			if (parent.equals(shopCategory)) {
				return ERROR_VIEW;
			}
			List<ShopCategory> children = shopCategoryService.findChildren(parent);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		shopCategoryService.update(shopCategory, "treePath", "grade", "children", "shops");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("shopCategoryTree", shopCategoryService.findTree());
		return "/admin/shop_category/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		ShopCategory shopCategory = shopCategoryService.find(id);
		if (shopCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<ShopCategory> children = shopCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.shopCategory.deleteExistChildrenNotAllowed");
		}
		Set<Shop> shops = shopCategory.getShops();
		if (shops != null && !shops.isEmpty()) {
			return Message.error("admin.shopCategory.deleteExistShopNotAllowed");
		}
		shopCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

}