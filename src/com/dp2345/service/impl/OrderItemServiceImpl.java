/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dp2345.dao.OrderItemDao;
import com.dp2345.entity.OrderItem;
import com.dp2345.service.OrderItemService;

/**
 * Service - 订单项
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("orderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, Long> implements OrderItemService {

	@Resource(name = "orderItemDaoImpl")
	public void setBaseDao(OrderItemDao orderItemDao) {
		super.setBaseDao(orderItemDao);
	}

}