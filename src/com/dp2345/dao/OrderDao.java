/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dp2345.Filter;
import com.dp2345.Page;
import com.dp2345.Pageable;
import com.dp2345.entity.Member;
import com.dp2345.entity.Order;
import com.dp2345.entity.Order.OrderStatus;
import com.dp2345.entity.Order.PaymentStatus;
import com.dp2345.entity.Order.ShippingStatus;

/**
 * Dao - 订单
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface OrderDao extends BaseDao<Order, Long> {

	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 订单，若不存在则返回null
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

}