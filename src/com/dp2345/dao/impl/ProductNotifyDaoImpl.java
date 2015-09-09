/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.dp2345.Page;
import com.dp2345.Pageable;
import com.dp2345.dao.ProductNotifyDao;
import com.dp2345.entity.Member;
import com.dp2345.entity.Product;
import com.dp2345.entity.ProductNotify;

/**
 * Dao - 到货通知
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("productNotifyDaoImpl")
public class ProductNotifyDaoImpl extends BaseDaoImpl<ProductNotify, Long> implements ProductNotifyDao {

	public boolean exists(Product product, String email) {
		String jpql = "select count(*) from ProductNotify productNotify where productNotify.product = :product and lower(productNotify.email) = lower(:email) and productNotify.hasSent = false";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("product", product).setParameter("email", email).getSingleResult();
		return count > 0;
	}

	public Page<ProductNotify> findPage(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductNotify> criteriaQuery = criteriaBuilder.createQuery(ProductNotify.class);
		Root<ProductNotify> root = criteriaQuery.from(ProductNotify.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product").get("isMarketable"), isMarketable));
		}
		if (isOutOfStock != null) {
			Path<Integer> stock = root.get("product").get("stock");
			Path<Integer> allocatedStock = root.get("product").get("allocatedStock");
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (hasSent != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("hasSent"), hasSent));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductNotify> criteriaQuery = criteriaBuilder.createQuery(ProductNotify.class);
		Root<ProductNotify> root = criteriaQuery.from(ProductNotify.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product").get("isMarketable"), isMarketable));
		}
		if (isOutOfStock != null) {
			Path<Integer> stock = root.get("product").get("stock");
			Path<Integer> allocatedStock = root.get("product").get("allocatedStock");
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (hasSent != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("hasSent"), hasSent));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}