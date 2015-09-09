/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import com.dp2345.entity.ProductImage;

/**
 * Service - 商品图片
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface ProductImageService {

	/**
	 * 生成商品图片
	 * 
	 * @param productImage
	 *            商品图片
	 */
	void build(ProductImage productImage);

}