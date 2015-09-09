/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import java.util.List;

import com.dp2345.entity.ShopCategory;

/**
 * Service - 店铺分类
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface ShopCategoryService extends BaseService<ShopCategory, Long> {

	/**
	 * 查找顶级店铺分类
	 * 
	 * @return 顶级店铺分类
	 */
	List<ShopCategory> findRoots();

	/**
	 * 查找顶级店铺分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级店铺分类
	 */
	List<ShopCategory> findRoots(Integer count);

	/**
	 * 查找顶级店铺分类(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 顶级店铺分类(缓存)
	 */
	List<ShopCategory> findRoots(Integer count, String cacheRegion);

	/**
	 * 查找上级店铺分类
	 * 
	 * @param shopCategory
	 *            店铺分类
	 * @return 上级店铺分类
	 */
	List<ShopCategory> findParents(ShopCategory shopCategory);

	/**
	 * 查找上级店铺分类
	 * 
	 * @param shopCategory
	 *            店铺分类
	 * @param count
	 *            数量
	 * @return 上级店铺分类
	 */
	List<ShopCategory> findParents(ShopCategory shopCategory, Integer count);

	/**
	 * 查找上级店铺分类(缓存)
	 * 
	 * @param shopCategory
	 *            店铺分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 上级店铺分类(缓存)
	 */
	List<ShopCategory> findParents(ShopCategory shopCategory, Integer count, String cacheRegion);

	/**
	 * 查找店铺分类树
	 * 
	 * @return 店铺分类树
	 */
	List<ShopCategory> findTree();

	/**
	 * 查找下级店铺分类
	 * 
	 * @param shopCategory
	 *            店铺分类
	 * @return 下级店铺分类
	 */
	List<ShopCategory> findChildren(ShopCategory shopCategory);

	/**
	 * 查找下级店铺分类
	 * 
	 * @param shopCategory
	 *            店铺分类
	 * @param count
	 *            数量
	 * @return 下级店铺分类
	 */
	List<ShopCategory> findChildren(ShopCategory shopCategory, Integer count);

	/**
	 * 查找下级店铺分类(缓存)
	 * 
	 * @param shopCategory
	 *            店铺分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 下级店铺分类(缓存)
	 */
	List<ShopCategory> findChildren(ShopCategory shopCategory, Integer count, String cacheRegion);

}