/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import java.util.List;

import com.dp2345.Filter;
import com.dp2345.Order;
import com.dp2345.entity.FriendLink;
import com.dp2345.entity.FriendLink.Type;

/**
 * Service - 友情链接
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface FriendLinkService extends BaseService<FriendLink, Long> {

	/**
	 * 查找友情链接
	 * 
	 * @param type
	 *            类型
	 * @return 友情链接
	 */
	List<FriendLink> findList(Type type);

	/**
	 * 查找友情链接(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param cacheRegion
	 *            缓存区域
	 * @return 友情链接(缓存)
	 */
	List<FriendLink> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion);

}