/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import java.util.List;

import com.dp2345.entity.ShopCategory;

/**
 * Dao - 店铺分类
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface ShopCategoryDao extends BaseDao<ShopCategory, Long> {

	/**
	 * 查找顶级店铺分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级店铺分类
	 */
	List<ShopCategory> findRoots(Integer count);

	/**
	 * 查找上级店铺分类
	 * 
	 * @param articleCategory
	 *            店铺分类
	 * @param count
	 *            数量
	 * @return 上级店铺分类
	 */
	List<ShopCategory> findParents(ShopCategory articleCategory, Integer count);

	/**
	 * 查找下级店铺分类
	 * 
	 * @param articleCategory
	 *            店铺分类
	 * @param count
	 *            数量
	 * @return 下级店铺分类
	 */
	List<ShopCategory> findChildren(ShopCategory articleCategory, Integer count);

}