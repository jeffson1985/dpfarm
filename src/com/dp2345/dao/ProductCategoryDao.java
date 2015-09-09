/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import java.util.List;

import com.dp2345.entity.ProductCategory;

/**
 * Dao - 商品分类
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface ProductCategoryDao extends BaseDao<ProductCategory, Long> {

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<ProductCategory> findRoots(Integer count);

	/**
	 * 查找上级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<ProductCategory> findParents(ProductCategory productCategory, Integer count);

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory, Integer count);

}