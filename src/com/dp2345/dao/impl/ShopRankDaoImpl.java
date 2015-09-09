/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import java.math.BigDecimal;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.dp2345.dao.ShopRankDao;
import com.dp2345.entity.ShopRank;

/**
 * Dao - 店铺等级
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("shopRankDaoImpl")
public class ShopRankDaoImpl extends BaseDaoImpl<ShopRank, Long> implements ShopRankDao {

	public boolean nameExists(String name) {
		if (name == null) {
			return false;
		}
		String jpql = "select count(*) from ShopRank shopRank where lower(shopRank.name) = lower(:name)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", name).getSingleResult();
		return count > 0;
	}

	public boolean amountExists(BigDecimal amount) {
		if (amount == null) {
			return false;
		}
		String jpql = "select count(*) from ShopRank shopRank where shopRank.amount = :amount";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("amount", amount).getSingleResult();
		return count > 0;
	}

	public ShopRank findDefault() {
		try {
			String jpql = "select shopRank from ShopRank shopRank where shopRank.isDefault = true";
			return entityManager.createQuery(jpql, ShopRank.class).setFlushMode(FlushModeType.COMMIT).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public ShopRank findByAmount(BigDecimal amount) {
		if (amount == null) {
			return null;
		}
		String jpql = "select shopRank from ShopRank shopRank where shopRank.isSpecial = false and shopRank.amount <= :amount order by shopRank.amount desc";
		return entityManager.createQuery(jpql, ShopRank.class).setFlushMode(FlushModeType.COMMIT).setParameter("amount", amount).setMaxResults(1).getSingleResult();
	}

	/**
	 * 处理默认并保存
	 * 
	 * @param shopRank
	 *            店铺等级
	 */
	@Override
	public void persist(ShopRank shopRank) {
		Assert.notNull(shopRank);
		if (shopRank.getIsDefault()) {
			String jpql = "update ShopRank shopRank set shopRank.isDefault = false where shopRank.isDefault = true";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
		}
		super.persist(shopRank);
	}

	/**
	 * 处理默认并更新
	 * 
	 * @param shopRank
	 *            店铺等级
	 * @return 店铺等级
	 */
	@Override
	public ShopRank merge(ShopRank shopRank) {
		Assert.notNull(shopRank);
		if (shopRank.getIsDefault()) {
			String jpql = "update ShopRank shopRank set shopRank.isDefault = false where shopRank.isDefault = true and shopRank != :shopRank";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("shopRank", shopRank).executeUpdate();
		}
		return super.merge(shopRank);
	}

	/**
	 * 忽略默认、清除会员价并删除
	 * 
	 * @param shopRank
	 *            店铺等级
	 */
	/*
	@Override
	public void remove(ShopRank shopRank) {
		if (shopRank != null && !shopRank.getIsDefault()) {
			String jpql = "select product from Product product join product.shopPrice shopPrice where index(shopPrice) = :shopRank";
			List<Product> products = entityManager.createQuery(jpql, Product.class).setFlushMode(FlushModeType.COMMIT).setParameter("shopRank", shopRank).getResultList();
			for (int i = 0; i < products.size(); i++) {
				Product product = products.get(i);
				product.getShopPrice().remove(shopRank);
				if (i % 20 == 0) {
					super.flush();
					super.clear();
				}
			}
			super.remove(super.merge(shopRank));
		}
	}
	*/

}