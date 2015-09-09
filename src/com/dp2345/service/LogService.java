/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import com.dp2345.entity.Log;

/**
 * Service - 日志
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface LogService extends BaseService<Log, Long> {

	/**
	 * 清空日志
	 */
	void clear();

}