/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import java.util.Iterator;
import java.util.List;

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
import com.dp2345.entity.Shop;
import com.dp2345.entity.ShopAttribute;
import com.dp2345.entity.BaseEntity.Save;
import com.dp2345.entity.ShopAttribute.Type;
import com.dp2345.service.ShopAttributeService;

/**
 * Controller - 店铺注册项
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminShopAttributeController")
@RequestMapping("/admin/shop_attribute")
public class ShopAttributeController extends BaseController {

	@Resource(name = "shopAttributeServiceImpl")
	private ShopAttributeService shopAttributeService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model, RedirectAttributes redirectAttributes) {
		if (shopAttributeService.count() - 8 >= Shop.ATTRIBUTE_VALUE_PROPERTY_COUNT) {
			addFlashMessage(redirectAttributes, Message.warn("admin.shopAttribute.addCountNotAllowed", Shop.ATTRIBUTE_VALUE_PROPERTY_COUNT));
		}
		return "/admin/shop_attribute/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ShopAttribute shopAttribute, RedirectAttributes redirectAttributes) {
		if (!isValid(shopAttribute, Save.class)) {
			return ERROR_VIEW;
		}
		if (shopAttribute.getType() == Type.select || shopAttribute.getType() == Type.checkbox) {
			List<String> options = shopAttribute.getOptions();
			if (options != null) {
				for (Iterator<String> iterator = options.iterator(); iterator.hasNext();) {
					String option = iterator.next();
					if (StringUtils.isEmpty(option)) {
						iterator.remove();
					}
				}
			}
			if (options == null || options.isEmpty()) {
				return ERROR_VIEW;
			}
		} else if (shopAttribute.getType() == Type.text) {
			shopAttribute.setOptions(null);
		} else {
			return ERROR_VIEW;
		}
		Integer propertyIndex = shopAttributeService.findUnusedPropertyIndex();
		if (propertyIndex == null) {
			return ERROR_VIEW;
		}
		shopAttribute.setPropertyIndex(propertyIndex);
		shopAttributeService.save(shopAttribute);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("shopAttribute", shopAttributeService.find(id));
		return "/admin/shop_attribute/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ShopAttribute shopAttribute, RedirectAttributes redirectAttributes) {
		if (!isValid(shopAttribute)) {
			return ERROR_VIEW;
		}
		ShopAttribute pShopAttribute = shopAttributeService.find(shopAttribute.getId());
		if (pShopAttribute == null) {
			return ERROR_VIEW;
		}
		if (pShopAttribute.getType() == Type.select || pShopAttribute.getType() == Type.checkbox) {
			List<String> options = shopAttribute.getOptions();
			if (options != null) {
				for (Iterator<String> iterator = options.iterator(); iterator.hasNext();) {
					String option = iterator.next();
					if (StringUtils.isEmpty(option)) {
						iterator.remove();
					}
				}
			}
			if (options == null || options.isEmpty()) {
				return ERROR_VIEW;
			}
		} else {
			shopAttribute.setOptions(null);
		}
		shopAttributeService.update(shopAttribute, "type", "propertyIndex");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", shopAttributeService.findPage(pageable));
		return "/admin/shop_attribute/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		shopAttributeService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}