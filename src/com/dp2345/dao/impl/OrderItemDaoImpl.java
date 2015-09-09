/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.OrderItemDao;
import com.dp2345.entity.OrderItem;

/**
 * Dao - 订单项
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {

}