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
import com.dp2345.entity.DeliveryCorp;
import com.dp2345.service.DeliveryCorpService;

/**
 * Controller - 物流公司
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminDeliveryCorpController")
@RequestMapping("/admin/delivery_corp")
public class DeliveryCorpController extends BaseController {

	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return "/admin/delivery_corp/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(DeliveryCorp deliveryCorp, RedirectAttributes redirectAttributes) {
		if (!isValid(deliveryCorp)) {
			return ERROR_VIEW;
		}
		deliveryCorp.setShippingMethods(null);
		deliveryCorpService.save(deliveryCorp);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("deliveryCorp", deliveryCorpService.find(id));
		return "/admin/delivery_corp/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(DeliveryCorp deliveryCorp, RedirectAttributes redirectAttributes) {
		if (!isValid(deliveryCorp)) {
			return ERROR_VIEW;
		}
		deliveryCorpService.update(deliveryCorp, "shippingMethods");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", deliveryCorpService.findPage(pageable));
		return "/admin/delivery_corp/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		deliveryCorpService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}