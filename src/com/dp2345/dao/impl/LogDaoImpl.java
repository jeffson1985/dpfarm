/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.LogDao;
import com.dp2345.entity.Log;

/**
 * Dao - 日志
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {

	public void removeAll() {
		String jpql = "delete from Log log";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
	}

}