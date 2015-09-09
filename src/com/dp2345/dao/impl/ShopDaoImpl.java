/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.ShopDao;
import com.dp2345.entity.Shop;
import com.dp2345.entity.Order;
import com.dp2345.entity.Product;
import com.dp2345.entity.Order.OrderStatus;
import com.dp2345.entity.Order.PaymentStatus;

/**
 * Dao - 店铺
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("shopDaoImpl")
public class ShopDaoImpl extends BaseDaoImpl<Shop, Long> implements ShopDao {

	public boolean shopAliasExists(String shopAlias) {
		if (shopAlias == null) {
			return false;
		}
		String jpql = "select count(*) from Shop shops where lower(shops.shop_alias) = lower(:shop_alias)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("shop_alias", shopAlias).getSingleResult();
		return count > 0;
	}

	public boolean emailExists(String email) {
		if (email == null) {
			return false;
		}
		String jpql = "select count(*) from Shop shops where lower(shops.email) = lower(:email)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("email", email).getSingleResult();
		return count > 0;
	}

	public Shop findByShopAlias(String username) {
		if (username == null) {
			return null;
		}
		try {
			String jpql = "select shops from Shop shops where lower(shops.shop_alias) = lower(:shop_alias)";
			return entityManager.createQuery(jpql, Shop.class).setFlushMode(FlushModeType.COMMIT).setParameter("shop_alias", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Shop> findListByEmail(String email) {
		if (email == null) {
			return Collections.<Shop> emptyList();
		}
		String jpql = "select shops from Shop shops where lower(shops.email) = lower(:email)";
		return entityManager.createQuery(jpql, Shop.class).setFlushMode(FlushModeType.COMMIT).setParameter("email", email).getResultList();
	}

	public List<Object[]> findSellList(Date beginDate, Date endDate, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Shop> shop = criteriaQuery.from(Shop.class);
		Join<Product, Order> orders = shop.join("orders");
		criteriaQuery.multiselect(shop.get("id"), shop.get("shop_alias"), shop.get("email"), shop.get("point"), shop.get("amount"), criteriaBuilder.sum(orders.<BigDecimal> get("amountPaid")));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(orders.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(orders.<Date> get("createDate"), endDate));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(orders.get("orderStatus"), OrderStatus.completed), criteriaBuilder.equal(orders.get("paymentStatus"), PaymentStatus.paid));
		criteriaQuery.where(restrictions);
		criteriaQuery.groupBy(shop.get("id"), shop.get("shop_alias"), shop.get("email"), shop.get("point"), shop.get("amount"));
		criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(orders.<BigDecimal> get("amountPaid"))));
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (count != null && count >= 0) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

}