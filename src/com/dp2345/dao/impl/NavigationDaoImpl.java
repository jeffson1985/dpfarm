/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.NavigationDao;
import com.dp2345.entity.Navigation;
import com.dp2345.entity.Navigation.Position;

/**
 * Dao - 导航
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("navigationDaoImpl")
public class NavigationDaoImpl extends BaseDaoImpl<Navigation, Long> implements NavigationDao {

	public List<Navigation> findList(Position position) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Navigation> criteriaQuery = criteriaBuilder.createQuery(Navigation.class);
		Root<Navigation> root = criteriaQuery.from(Navigation.class);
		criteriaQuery.select(root);
		if (position != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("position"), position));
		}
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

}