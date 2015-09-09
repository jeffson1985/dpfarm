/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import com.dp2345.entity.Cart;

/**
 * Dao - 购物车
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface CartDao extends BaseDao<Cart, Long> {

	/**
	 * 清除过期购物车
	 */
	void evictExpired();

}