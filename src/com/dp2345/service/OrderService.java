/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dp2345.Filter;
import com.dp2345.Page;
import com.dp2345.Pageable;
import com.dp2345.entity.Admin;
import com.dp2345.entity.Cart;
import com.dp2345.entity.CouponCode;
import com.dp2345.entity.Member;
import com.dp2345.entity.Order;
import com.dp2345.entity.Payment;
import com.dp2345.entity.PaymentMethod;
import com.dp2345.entity.Receiver;
import com.dp2345.entity.Refunds;
import com.dp2345.entity.Returns;
import com.dp2345.entity.Shipping;
import com.dp2345.entity.ShippingMethod;
import com.dp2345.entity.Order.OrderStatus;
import com.dp2345.entity.Order.PaymentStatus;
import com.dp2345.entity.Order.ShippingStatus;

/**
 * Service - 订单
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface OrderService extends BaseService<Order, Long> {

	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 若不存在则返回null
	 */
	Order findBySn(String sn);

	/**
	 * 查找订单
	 * 
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 订单
	 */
	List<Order> findList(Member member, Integer count, List<Filter> filters, List<com.dp2345.Order> orders);

	/**
	 * 查找订单分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Order> findPage(Member member, Pageable pageable);

	/**
	 * 查找订单分页
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param paymentStatus
	 *            支付状态
	 * @param shippingStatus
	 *            配送状态
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Order> findPage(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired, Pageable pageable);

	/**
	 * 查询订单数量
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param paymentStatus
	 *            支付状态
	 * @param shippingStatus
	 *            配送状态
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long count(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired);

	/**
	 * 查询等待支付订单数量
	 * 
	 * @param member
	 *            会员
	 * @return 等待支付订单数量
	 */
	Long waitingPaymentCount(Member member);

	/**
	 * 查询等待发货订单数量
	 * 
	 * @param member
	 *            会员
	 * @return 等待发货订单数量
	 */
	Long waitingShippingCount(Member member);

	/**
	 * 获取销售额
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 销售额
	 */
	BigDecimal getSalesAmount(Date beginDate, Date endDate);

	/**
	 * 获取销售量
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 销售量
	 */
	Integer getSalesVolume(Date beginDate, Date endDate);

	/**
	 * 释放过期订单库存
	 */
	void releaseStock();

	/**
	 * 生成订单
	 * 
	 * @param cart
	 *            购物车
	 * @param receiver
	 *            收货地址
	 * @param paymentMethod
	 *            支付方式
	 * @param shippingMethod
	 *            配送方式
	 * @param couponCode
	 *            优惠码
	 * @param isInvoice
	 *            是否开据发票
	 * @param invoiceTitle
	 *            发票抬头
	 * @param useBalance
	 *            是否使用余额
	 * @param memo
	 *            附言
	 * @return 订单
	 */
	Order build(Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo);

	/**
	 * 创建订单
	 * 
	 * @param cart
	 *            购物车
	 * @param receiver
	 *            收货地址
	 * @param paymentMethod
	 *            支付方式
	 * @param shippingMethod
	 *            配送方式
	 * @param couponCode
	 *            优惠码
	 * @param isInvoice
	 *            是否开据发票
	 * @param invoiceTitle
	 *            发票抬头
	 * @param useBalance
	 *            是否使用余额
	 * @param memo
	 *            附言
	 * @param operator
	 *            操作员
	 * @return 订单
	 */
	Order create(Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo, Admin operator);

	/**
	 * 更新订单
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void update(Order order, Admin operator);

	/**
	 * 订单确认
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void confirm(Order order, Admin operator);

	/**
	 * 订单完成
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void complete(Order order, Admin operator);

	/**
	 * 订单取消
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void cancel(Order order, Admin operator);

	/**
	 * 订单支付
	 * 
	 * @param order
	 *            订单
	 * @param payment
	 *            收款单
	 * @param operator
	 *            操作员
	 */
	void payment(Order order, Payment payment, Admin operator);

	/**
	 * 订单退款
	 * 
	 * @param order
	 *            订单
	 * @param refunds
	 *            退款单
	 * @param operator
	 *            操作员
	 */
	void refunds(Order order, Refunds refunds, Admin operator);

	/**
	 * 订单发货
	 * 
	 * @param order
	 *            订单
	 * @param shipping
	 *            发货单
	 * @param operator
	 *            操作员
	 */
	void shipping(Order order, Shipping shipping, Admin operator);

	/**
	 * 订单退货
	 * 
	 * @param order
	 *            订单
	 * @param returns
	 *            退货单
	 * @param operator
	 *            操作员
	 */
	void returns(Order order, Returns returns, Admin operator);

}