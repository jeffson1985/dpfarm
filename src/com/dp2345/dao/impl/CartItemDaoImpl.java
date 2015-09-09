/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.CartItemDao;
import com.dp2345.entity.CartItem;

/**
 * Dao - 购物车项
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("cartItemDaoImpl")
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, Long> implements CartItemDao {

}