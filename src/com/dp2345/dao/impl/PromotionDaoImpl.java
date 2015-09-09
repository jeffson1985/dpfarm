/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.dp2345.Filter;
import com.dp2345.Order;
import com.dp2345.dao.PromotionDao;
import com.dp2345.entity.Promotion;

/**
 * Dao - 促销
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("promotionDaoImpl")
public class PromotionDaoImpl extends BaseDaoImpl<Promotion, Long> implements PromotionDao {

	public List<Promotion> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
		Root<Promotion> root = criteriaQuery.from(Promotion.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (hasBegun != null) {
			if (hasBegun) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("beginDate"), new Date())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, root.get("beginDate").isNotNull(), criteriaBuilder.greaterThan(root.<Date> get("beginDate"), new Date()));
			}
		}
		if (hasEnded != null) {
			if (hasEnded) {
				restrictions = criteriaBuilder.and(restrictions, root.get("endDate").isNotNull(), criteriaBuilder.lessThan(root.<Date> get("endDate"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("endDate").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("endDate"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

}