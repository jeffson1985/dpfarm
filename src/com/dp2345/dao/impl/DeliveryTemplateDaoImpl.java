/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.dp2345.dao.DeliveryTemplateDao;
import com.dp2345.entity.DeliveryTemplate;

/**
 * Dao - 快递单模板
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("deliveryTemplateDaoImpl")
public class DeliveryTemplateDaoImpl extends BaseDaoImpl<DeliveryTemplate, Long> implements DeliveryTemplateDao {

	public DeliveryTemplate findDefault() {
		try {
			String jpql = "select deliveryTemplate from DeliveryTemplate deliveryTemplate where deliveryTemplate.isDefault = true";
			return entityManager.createQuery(jpql, DeliveryTemplate.class).setFlushMode(FlushModeType.COMMIT).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * 处理默认并保存
	 * 
	 * @param deliveryTemplate
	 *            快递单模板
	 */
	@Override
	public void persist(DeliveryTemplate deliveryTemplate) {
		Assert.notNull(deliveryTemplate);
		if (deliveryTemplate.getIsDefault()) {
			String jpql = "update DeliveryTemplate deliveryTemplate set deliveryTemplate.isDefault = false where deliveryTemplate.isDefault = true";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
		}
		super.persist(deliveryTemplate);
	}

	/**
	 * 处理默认并更新
	 * 
	 * @param deliveryTemplate
	 *            快递单模板
	 * @return 快递单模板
	 */
	@Override
	public DeliveryTemplate merge(DeliveryTemplate deliveryTemplate) {
		Assert.notNull(deliveryTemplate);
		if (deliveryTemplate.getIsDefault()) {
			String jpql = "update DeliveryTemplate deliveryTemplate set deliveryTemplate.isDefault = false where deliveryTemplate.isDefault = true and deliveryTemplate != :deliveryTemplate";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("deliveryTemplate", deliveryTemplate).executeUpdate();
		}
		return super.merge(deliveryTemplate);
	}

}