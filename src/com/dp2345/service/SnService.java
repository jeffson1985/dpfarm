/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import com.dp2345.entity.Sn.Type;

/**
 * Service - 序列号
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface SnService {

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	String generate(Type type);

}