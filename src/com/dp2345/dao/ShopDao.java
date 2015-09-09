/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import java.util.Date;
import java.util.List;

import com.dp2345.entity.Shop;

/**
 * Dao - 会员
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface ShopDao extends BaseDao<Shop, Long> {

	/**
	 * 判断店铺别名是否存在
	 * 
	 * @param username
	 *            店铺别名(忽略大小写)
	 * @return 店铺别名是否存在
	 */
	boolean shopAliasExists(String shopAlias);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 根据店铺别名查找店铺
	 * 
	 * @param storeAlias
	 *           店铺别名(忽略大小写)
	 * @return 店铺，若不存在则返回null
	 */
	Shop findByShopAlias(String shopAlias);

	/**
	 * 根据E-mail查找店铺
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 店铺，若不存在则返回null
	 */
	List<Shop> findListByEmail(String email);
	
	
	/**
	 * 根据E-mail查找店铺
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 店铺，若不存在则返回null
	 */
	//List<Shop> findListByArea(Long ... areaId);

	/**
	 * 查找店铺贩卖信息
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param count
	 *            数量
	 * @return 店铺贩卖信息
	 */
	List<Object[]> findSellList(Date beginDate, Date endDate, Integer count);

}