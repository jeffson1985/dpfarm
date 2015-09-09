/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import com.dp2345.entity.Seo;
import com.dp2345.entity.Seo.Type;

/**
 * Dao - SEO设置
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface SeoDao extends BaseDao<Seo, Long> {

	/**
	 * 查找SEO设置
	 * 
	 * @param type
	 *            类型
	 * @return SEO设置
	 */
	Seo find(Type type);

}