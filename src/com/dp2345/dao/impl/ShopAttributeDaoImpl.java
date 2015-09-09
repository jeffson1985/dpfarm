/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.ShopAttributeDao;
import com.dp2345.entity.Shop;
import com.dp2345.entity.ShopAttribute;
import com.dp2345.entity.ShopAttribute.Type;

/**
 * Dao - 店铺注册项
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("shopAttributeDaoImpl")
public class ShopAttributeDaoImpl extends BaseDaoImpl<ShopAttribute, Long> implements ShopAttributeDao {

	public Integer findUnusedPropertyIndex() {
		for (int i = 0; i < Shop.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String jpql = "select count(*) from ShopAttribute shopAttribute where shopAttribute.propertyIndex = :propertyIndex";
			Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("propertyIndex", i).getSingleResult();
			if (count == 0) {
				return i;
			}
		}
		return null;
	}

	public List<ShopAttribute> findList() {
		String jpql = "select shopAttribute from ShopAttribute shopAttribute where shopAttribute.isEnabled = true order by shopAttribute.order asc";
		return entityManager.createQuery(jpql, ShopAttribute.class).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

	/**
	 * 清除店铺注册项值
	 * 
	 * @param shopAttribute
	 *            会员注册项
	 */
	@Override
	public void remove(ShopAttribute shopAttribute) {
		if (shopAttribute != null && (shopAttribute.getType() == Type.text || shopAttribute.getType() == Type.select || shopAttribute.getType() == Type.checkbox)) {
			String propertyName = Shop.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + shopAttribute.getPropertyIndex();
			String jpql = "update Shop shops set shops." + propertyName + " = null";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
			super.remove(shopAttribute);
		}
	}

}