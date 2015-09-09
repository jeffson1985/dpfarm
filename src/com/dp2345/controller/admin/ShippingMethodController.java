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
import com.dp2345.entity.ShippingMethod;
import com.dp2345.service.DeliveryCorpService;
import com.dp2345.service.ShippingMethodService;

/**
 * Controller - 配送方式
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminShippingMethodController")
@RequestMapping("/admin/shipping_method")
public class ShippingMethodController extends BaseController {

	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		return "/admin/shipping_method/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ShippingMethod shippingMethod, Long defaultDeliveryCorpId, RedirectAttributes redirectAttributes) {
		shippingMethod.setDefaultDeliveryCorp(deliveryCorpService.find(defaultDeliveryCorpId));
		if (!isValid(shippingMethod)) {
			return ERROR_VIEW;
		}
		shippingMethod.setPaymentMethods(null);
		shippingMethod.setOrders(null);
		shippingMethodService.save(shippingMethod);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		model.addAttribute("shippingMethod", shippingMethodService.find(id));
		return "/admin/shipping_method/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ShippingMethod shippingMethod, Long defaultDeliveryCorpId, RedirectAttributes redirectAttributes) {
		shippingMethod.setDefaultDeliveryCorp(deliveryCorpService.find(defaultDeliveryCorpId));
		if (!isValid(shippingMethod)) {
			return ERROR_VIEW;
		}
		shippingMethodService.update(shippingMethod, "paymentMethods", "orders");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", shippingMethodService.findPage(pageable));
		return "/admin/shipping_method/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids.length >= shippingMethodService.count()) {
			return Message.error("admin.common.deleteAllNotAllowed");
		}
		shippingMethodService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}