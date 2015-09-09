/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import java.math.BigDecimal;

import com.dp2345.entity.ShopRank;

/**
 * Service - 店铺等级
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface ShopRankService extends BaseService<ShopRank, Long> {

	/**
	 * 判断名称是否存在
	 * 
	 * @param name
	 *            名称(忽略大小写)
	 * @return 名称是否存在
	 */
	boolean nameExists(String name);

	/**
	 * 判断名称是否唯一
	 * 
	 * @param previousName
	 *            修改前名称(忽略大小写)
	 * @param currentName
	 *            当前名称(忽略大小写)
	 * @return 名称是否唯一
	 */
	boolean nameUnique(String previousName, String currentName);

	/**
	 * 判断消费金额是否存在
	 * 
	 * @param amount
	 *            消费金额
	 * @return 消费金额是否存在
	 */
	boolean amountExists(BigDecimal amount);

	/**
	 * 判断消费金额是否唯一
	 * 
	 * @param previousAmount
	 *            修改前消费金额
	 * @param currentAmount
	 *            当前消费金额
	 * @return 消费金额是否唯一
	 */
	boolean amountUnique(BigDecimal previousAmount, BigDecimal currentAmount);

	/**
	 * 查找默认店铺等级
	 * 
	 * @return 默认店铺等级，若不存在则返回null
	 */
	ShopRank findDefault();

	/**
	 * 根据消费金额查找符合此条件的最高店铺等级
	 * 
	 * @param amount
	 *            消费金额
	 * @return 店铺等级，不包含特殊店铺等级，若不存在则返回null
	 */
	ShopRank findByAmount(BigDecimal amount);

}