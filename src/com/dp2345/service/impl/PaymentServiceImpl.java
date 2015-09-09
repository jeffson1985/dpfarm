/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dp2345.dao.PaymentDao;
import com.dp2345.entity.Member;
import com.dp2345.entity.Order;
import com.dp2345.entity.Payment;
import com.dp2345.entity.Payment.Status;
import com.dp2345.entity.Payment.Type;
import com.dp2345.service.MemberService;
import com.dp2345.service.OrderService;
import com.dp2345.service.PaymentService;

/**
 * Service - 收款单
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("paymentServiceImpl")
public class PaymentServiceImpl extends BaseServiceImpl<Payment, Long> implements PaymentService {

	@Resource(name = "paymentDaoImpl")
	private PaymentDao paymentDao;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "paymentDaoImpl")
	public void setBaseDao(PaymentDao paymentDao) {
		super.setBaseDao(paymentDao);
	}

	@Transactional(readOnly = true)
	public Payment findBySn(String sn) {
		return paymentDao.findBySn(sn);
	}

	public void handle(Payment payment) {
		paymentDao.refresh(payment, LockModeType.PESSIMISTIC_WRITE);
		if (payment != null && payment.getStatus() == Status.wait) {
			if (payment.getType() == Type.payment) {
				Order order = payment.getOrder();
				if (order != null) {
					orderService.payment(order, payment, null);
				}
			} else if (payment.getType() == Type.recharge) {
				Member member = payment.getMember();
				if (member != null) {
					memberService.update(member, null, payment.getEffectiveAmount(), null, null);
				}
			}
			payment.setStatus(Status.success);
			payment.setPaymentDate(new Date());
			paymentDao.merge(payment);
		}
	}

}