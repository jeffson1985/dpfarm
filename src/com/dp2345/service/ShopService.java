/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dp2345.entity.Admin;
import com.dp2345.entity.Member;
import com.dp2345.entity.Shop;

/**
 * Service - 店铺
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface ShopService extends BaseService<Shop, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean shopAliasExists(String shopAlias);

	/**
	 * 判断用户名是否禁用
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否禁用
	 */
	boolean shopAliasDisabled(String shopAlias);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 判断E-mail是否唯一
	 * 
	 * @param previousEmail
	 *            修改前E-mail(忽略大小写)
	 * @param currentEmail
	 *            当前E-mail(忽略大小写)
	 * @return E-mail是否唯一
	 */
	boolean emailUnique(String previousEmail, String currentEmail);

	/**
	 * 保存店铺
	 * 
	 * @param shop
	 *            店铺
	 * @param operator
	 *            操作员
	 */
	void save(Shop shop, Admin operator);

	/**
	 * 更新店铺
	 * 
	 * @param shop
	 *            店铺
	 * @param modifyPoint
	 *            修改积分
	 * @param modifyBalance
	 *            修改余额
	 * @param depositMemo
	 *            修改余额备注
	 * @param operator
	 *            操作员
	 */
	void update(Shop shop, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo, Admin operator);

	/**
	 * 根据用户名查找店铺
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 店铺，若不存在则返回null
	 */
	Shop findByshopAlias(String shopAlias);

	/**
	 * 根据E-mail查找店铺
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 店铺，若不存在则返回null
	 */
	List<Shop> findListByEmail(String email);

	/**
	 * 查找店铺卖出信息
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param count
	 *            数量
	 * @return 店铺卖出信息
	 */
	List<Object[]> findSellList(Date beginDate, Date endDate, Integer count);

	/**
	 * 判断店铺是否登录
	 * 
	 * @return 店铺是否登录
	 */
	boolean isAuthenticated();

	/**
	 * 获取当前登录店铺
	 * 
	 * @return 当前登录店铺，若不存在则返回null
	 */
	Member getCurrent();

	/**
	 * 获取当前登录用户名
	 * 
	 * @return 当前登录用户名，若不存在则返回null
	 */
	String getCurrentUsername();

}