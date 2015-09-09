/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import java.util.HashSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.Message;
import com.dp2345.Pageable;
import com.dp2345.entity.PaymentMethod;
import com.dp2345.entity.ShippingMethod;
import com.dp2345.entity.PaymentMethod.Method;
import com.dp2345.service.PaymentMethodService;
import com.dp2345.service.ShippingMethodService;

/**
 * Controller - 支付方式
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminPaymentMethodController")
@RequestMapping("/admin/payment_method")
public class PaymentMethodController extends BaseController {

	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("methods", Method.values());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		return "/admin/payment_method/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(PaymentMethod paymentMethod, Long[] shippingMethodIds, RedirectAttributes redirectAttributes) {
		paymentMethod.setShippingMethods(new HashSet<ShippingMethod>(shippingMethodService.findList(shippingMethodIds)));
		if (!isValid(paymentMethod)) {
			return ERROR_VIEW;
		}
		paymentMethod.setOrders(null);
		paymentMethodService.save(paymentMethod);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("methods", Method.values());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		model.addAttribute("paymentMethod", paymentMethodService.find(id));
		return "/admin/payment_method/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(PaymentMethod paymentMethod, Long[] shippingMethodIds, RedirectAttributes redirectAttributes) {
		paymentMethod.setShippingMethods(new HashSet<ShippingMethod>(shippingMethodService.findList(shippingMethodIds)));
		if (!isValid(paymentMethod)) {
			return ERROR_VIEW;
		}
		paymentMethodService.update(paymentMethod, "orders");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", paymentMethodService.findPage(pageable));
		return "/admin/payment_method/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids.length >= paymentMethodService.count()) {
			return Message.error("admin.common.deleteAllNotAllowed");
		}
		paymentMethodService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}