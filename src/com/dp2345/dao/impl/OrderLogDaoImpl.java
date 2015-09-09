/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.OrderLogDao;
import com.dp2345.entity.OrderLog;

/**
 * Dao - 订单日志
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("orderLogDaoImpl")
public class OrderLogDaoImpl extends BaseDaoImpl<OrderLog, Long> implements OrderLogDao {

}