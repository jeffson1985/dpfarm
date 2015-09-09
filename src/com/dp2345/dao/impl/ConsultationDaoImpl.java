/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.dp2345.Filter;
import com.dp2345.Order;
import com.dp2345.Page;
import com.dp2345.Pageable;
import com.dp2345.dao.ConsultationDao;
import com.dp2345.entity.Consultation;
import com.dp2345.entity.Member;
import com.dp2345.entity.Product;

/**
 * Dao - 咨询
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("consultationDaoImpl")
public class ConsultationDaoImpl extends BaseDaoImpl<Consultation, Long> implements ConsultationDao {

	public List<Consultation> findList(Member member, Product product, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Consultation> criteriaQuery = criteriaBuilder.createQuery(Consultation.class);
		Root<Consultation> root = criteriaQuery.from(Consultation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forConsultation")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (product != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public Page<Consultation> findPage(Member member, Product product, Boolean isShow, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Consultation> criteriaQuery = criteriaBuilder.createQuery(Consultation.class);
		Root<Consultation> root = criteriaQuery.from(Consultation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forConsultation")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (product != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Member member, Product product, Boolean isShow) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Consultation> criteriaQuery = criteriaBuilder.createQuery(Consultation.class);
		Root<Consultation> root = criteriaQuery.from(Consultation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forConsultation")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (product != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}