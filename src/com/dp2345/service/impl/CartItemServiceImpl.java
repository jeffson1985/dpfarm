/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dp2345.dao.CartItemDao;
import com.dp2345.entity.CartItem;
import com.dp2345.service.CartItemService;

/**
 * Service - 购物车项
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("cartItemServiceImpl")
public class CartItemServiceImpl extends BaseServiceImpl<CartItem, Long> implements CartItemService {

	@Resource(name = "cartItemDaoImpl")
	public void setBaseDao(CartItemDao cartItemDao) {
		super.setBaseDao(cartItemDao);
	}

}