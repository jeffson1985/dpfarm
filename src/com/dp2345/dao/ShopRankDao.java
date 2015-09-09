/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import java.math.BigDecimal;

import com.dp2345.entity.ShopRank;

/**
 * Dao - 店铺等级
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface ShopRankDao extends BaseDao<ShopRank, Long> {

	/**
	 * 判断名称是否存在
	 * 
	 * @param name
	 *            名称(忽略大小写)
	 * @return 名称是否存在
	 */
	boolean nameExists(String name);

	/**
	 * 判断贩卖金额是否存在
	 * 
	 * @param amount
	 *            贩卖金额
	 * @return 贩卖金额是否存在
	 */
	boolean amountExists(BigDecimal amount);

	/**
	 * 查找默认会员等级
	 * 
	 * @return 默认会员等级，若不存在则返回null
	 */
	ShopRank findDefault();

	/**
	 * 根据贩卖金额查找符合此条件的最高店铺等级
	 * 
	 * @param amount
	 *            贩卖金额
	 * @return 店铺等级，不包含特殊店铺等级，若不存在则返回null
	 */
	ShopRank findByAmount(BigDecimal amount);

}