/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import com.dp2345.entity.DeliveryTemplate;

/**
 * Dao - 快递单模板
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface DeliveryTemplateDao extends BaseDao<DeliveryTemplate, Long> {

	/**
	 * 查找默认快递单模板
	 * 
	 * @return 默认快递单模板，若不存在则返回null
	 */
	DeliveryTemplate findDefault();

}