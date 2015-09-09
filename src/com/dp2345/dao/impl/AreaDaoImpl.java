/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.AreaDao;
import com.dp2345.entity.Area;

/**
 * Dao - 地区
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> implements AreaDao {

	public List<Area> findRoots(Integer count) {
		String jpql = "select area from Area area where area.parent is null order by area.order asc";
		TypedQuery<Area> query = entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

}