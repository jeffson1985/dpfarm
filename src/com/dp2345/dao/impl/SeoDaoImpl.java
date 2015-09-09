/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.SeoDao;
import com.dp2345.entity.Seo;
import com.dp2345.entity.Seo.Type;

/**
 * Dao - SEO设置
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("seoDaoImpl")
public class SeoDaoImpl extends BaseDaoImpl<Seo, Long> implements SeoDao {

	public Seo find(Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select seo from Seo seo where seo.type = :type";
			return entityManager.createQuery(jpql, Seo.class).setFlushMode(FlushModeType.COMMIT).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}