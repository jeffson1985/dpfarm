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

import com.dp2345.Message;
import com.dp2345.Pageable;
import com.dp2345.entity.Payment;
import com.dp2345.service.PaymentService;

/**
 * Controller - 收款单
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminPaymentController")
@RequestMapping("/admin/payment")
public class PaymentController extends BaseController {

	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		model.addAttribute("payment", paymentService.find(id));
		return "/admin/payment/view";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", paymentService.findPage(pageable));
		return "/admin/payment/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Payment payment = paymentService.find(id);
				if (payment != null && payment.getExpire() != null && !payment.hasExpired()) {
					return Message.error("admin.payment.deleteUnexpiredNotAllowed");
				}
			}
			paymentService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}