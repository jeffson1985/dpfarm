/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.mall;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dp2345.Setting;
import com.dp2345.entity.Member;
import com.dp2345.entity.Order;
import com.dp2345.entity.Payment;
import com.dp2345.entity.PaymentMethod;
import com.dp2345.entity.Sn;
import com.dp2345.entity.Order.PaymentStatus;
import com.dp2345.entity.Payment.Method;
import com.dp2345.entity.Payment.Status;
import com.dp2345.entity.Payment.Type;
import com.dp2345.plugin.PaymentPlugin;
import com.dp2345.plugin.PaymentPlugin.NotifyMethod;
import com.dp2345.service.MemberService;
import com.dp2345.service.OrderService;
import com.dp2345.service.PaymentService;
import com.dp2345.service.PluginService;
import com.dp2345.service.SnService;
import com.dp2345.util.SettingUtils;

/**
 * Controller - 支付
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("shopPaymentController")
@RequestMapping("/payment")
public class PaymentController extends BaseController {

	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	@Resource(name = "snServiceImpl")
	private SnService snService;

	/**
	 * 提交
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(Type type, String paymentPluginId, String sn, BigDecimal amount, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Member member = memberService.getCurrent();
		if (member == null) {
			return ERROR_VIEW;
		}
		PaymentPlugin paymentPlugin = pluginService.getPaymentPlugin(paymentPluginId);
		if (paymentPlugin == null || !paymentPlugin.getIsEnabled()) {
			return ERROR_VIEW;
		}
		Payment payment = new Payment();
		String description = null;
		if (type == Type.payment) {
			Order order = orderService.findBySn(sn);
			if (order == null || !member.equals(order.getMember()) || order.isExpired() || order.isLocked(null)) {
				return ERROR_VIEW;
			}
			if (order.getPaymentMethod() == null || order.getPaymentMethod().getMethod() != PaymentMethod.Method.online) {
				return ERROR_VIEW;
			}
			if (order.getPaymentStatus() != PaymentStatus.unpaid && order.getPaymentStatus() != PaymentStatus.partialPayment) {
				return ERROR_VIEW;
			}
			if (order.getAmountPayable().compareTo(new BigDecimal(0)) <= 0) {
				return ERROR_VIEW;
			}
			payment.setSn(snService.generate(Sn.Type.payment));
			payment.setType(Type.payment);
			payment.setMethod(Method.online);
			payment.setStatus(Status.wait);
			payment.setPaymentMethod(order.getPaymentMethodName() + Payment.PAYMENT_METHOD_SEPARATOR + paymentPlugin.getPaymentName());
			payment.setFee(paymentPlugin.calculateFee(order.getAmountPayable()));
			payment.setAmount(paymentPlugin.calculateAmount(order.getAmountPayable()));
			payment.setPaymentPluginId(paymentPluginId);
			payment.setExpire(paymentPlugin.getTimeout() != null ? DateUtils.addMinutes(new Date(), paymentPlugin.getTimeout()) : null);
			payment.setOrder(order);
			paymentService.save(payment);
			description = order.getName();
		} else if (type == Type.recharge) {
			Setting setting = SettingUtils.get();
			if (amount == null || amount.compareTo(new BigDecimal(0)) <= 0 || amount.precision() > 15 || amount.scale() > setting.getPriceScale()) {
				return ERROR_VIEW;
			}
			payment.setSn(snService.generate(Sn.Type.payment));
			payment.setType(Type.recharge);
			payment.setMethod(Method.online);
			payment.setStatus(Status.wait);
			payment.setPaymentMethod(paymentPlugin.getPaymentName());
			payment.setFee(paymentPlugin.calculateFee(amount));
			payment.setAmount(paymentPlugin.calculateAmount(amount));
			payment.setPaymentPluginId(paymentPluginId);
			payment.setExpire(paymentPlugin.getTimeout() != null ? DateUtils.addMinutes(new Date(), paymentPlugin.getTimeout()) : null);
			payment.setMember(member);
			paymentService.save(payment);
			description = message("shop.member.deposit.recharge");
		} else {
			return ERROR_VIEW;
		}
		model.addAttribute("requestUrl", paymentPlugin.getRequestUrl());
		model.addAttribute("requestMethod", paymentPlugin.getRequestMethod());
		model.addAttribute("requestCharset", paymentPlugin.getRequestCharset());
		model.addAttribute("parameterMap", paymentPlugin.getParameterMap(payment.getSn(), description, request));
		if (StringUtils.isNotEmpty(paymentPlugin.getRequestCharset())) {
			response.setContentType("text/html; charset=" + paymentPlugin.getRequestCharset());
		}
		return "shop/payment/submit";
	}

	/**
	 * 通知
	 */
	@RequestMapping("/notify/{notifyMethod}/{sn}")
	public String notify(@PathVariable NotifyMethod notifyMethod, @PathVariable String sn, HttpServletRequest request, ModelMap model) {
		Payment payment = paymentService.findBySn(sn);
		if (payment != null) {
			PaymentPlugin paymentPlugin = pluginService.getPaymentPlugin(payment.getPaymentPluginId());
			if (paymentPlugin != null) {
				if (paymentPlugin.verifyNotify(sn, notifyMethod, request)) {
					paymentService.handle(payment);
				}
				model.addAttribute("notifyMessage", paymentPlugin.getNotifyMessage(sn, notifyMethod, request));
			}
			model.addAttribute("payment", payment);
		}
		return "shop/payment/notify";
	}

}