/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.ShippingMethodDao;
import com.dp2345.entity.ShippingMethod;

/**
 * Dao - 配送方式
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("shippingMethodDaoImpl")
public class ShippingMethodDaoImpl extends BaseDaoImpl<ShippingMethod, Long> implements ShippingMethodDao {

}