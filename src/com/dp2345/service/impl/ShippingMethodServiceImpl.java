/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dp2345.dao.ShippingMethodDao;
import com.dp2345.entity.ShippingMethod;
import com.dp2345.service.ShippingMethodService;

/**
 * Service - 配送方式
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("shippingMethodServiceImpl")
public class ShippingMethodServiceImpl extends BaseServiceImpl<ShippingMethod, Long> implements ShippingMethodService {

	@Resource(name = "shippingMethodDaoImpl")
	public void setBaseDao(ShippingMethodDao shippingMethodDao) {
		super.setBaseDao(shippingMethodDao);
	}

}