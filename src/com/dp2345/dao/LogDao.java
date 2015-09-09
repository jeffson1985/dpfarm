/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import com.dp2345.entity.Log;

/**
 * Dao - 日志
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface LogDao extends BaseDao<Log, Long> {

	/**
	 * 删除所有日志
	 */
	void removeAll();

}