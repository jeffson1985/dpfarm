/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import com.dp2345.Page;
import com.dp2345.Pageable;
import com.dp2345.entity.Member;
import com.dp2345.entity.Product;
import com.dp2345.entity.ProductNotify;

/**
 * Dao - 到货通知
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface ProductNotifyDao extends BaseDao<ProductNotify, Long> {

	/**
	 * 判断到货通知是否存在
	 * 
	 * @param product
	 *            商品
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 到货通知是否存在
	 */
	boolean exists(Product product, String email);

	/**
	 * 查找到货通知分页
	 * 
	 * @param member
	 *            会员
	 * @param isMarketable
	 *            是否上架
	 * @param isOutOfStock
	 *            商品是否缺货
	 * @param hasSent
	 *            是否已发送.
	 * @param pageable
	 *            分页信息
	 * @return 到货通知分页
	 */
	Page<ProductNotify> findPage(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable);

	/**
	 * 查找到货通知数量
	 * 
	 * @param member
	 *            会员
	 * @param isMarketable
	 *            是否上架
	 * @param isOutOfStock
	 *            商品是否缺货
	 * @param hasSent
	 *            是否已发送.
	 * @return 到货通知数量
	 */
	Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent);

}