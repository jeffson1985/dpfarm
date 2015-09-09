/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import java.math.BigDecimal;

import com.dp2345.entity.MemberRank;

/**
 * Dao - 会员等级
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface MemberRankDao extends BaseDao<MemberRank, Long> {

	/**
	 * 判断名称是否存在
	 * 
	 * @param name
	 *            名称(忽略大小写)
	 * @return 名称是否存在
	 */
	boolean nameExists(String name);

	/**
	 * 判断消费金额是否存在
	 * 
	 * @param amount
	 *            消费金额
	 * @return 消费金额是否存在
	 */
	boolean amountExists(BigDecimal amount);

	/**
	 * 查找默认会员等级
	 * 
	 * @return 默认会员等级，若不存在则返回null
	 */
	MemberRank findDefault();

	/**
	 * 根据消费金额查找符合此条件的最高会员等级
	 * 
	 * @param amount
	 *            消费金额
	 * @return 会员等级，不包含特殊会员等级，若不存在则返回null
	 */
	MemberRank findByAmount(BigDecimal amount);

}