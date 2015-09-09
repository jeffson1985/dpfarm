/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.dp2345.dao.ShopCategoryDao;
import com.dp2345.entity.ShopCategory;

/**
 * Dao - 店铺分类
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("shopCategoryDaoImpl")
public class ShopCategoryDaoImpl extends BaseDaoImpl<ShopCategory, Long> implements ShopCategoryDao {

	public List<ShopCategory> findRoots(Integer count) {
		String jpql = "select shopCategory from ShopCategory shopCategory where shopCategory.parent is null order by shopCategory.order asc";
		TypedQuery<ShopCategory> query = entityManager.createQuery(jpql, ShopCategory.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ShopCategory> findParents(ShopCategory shopCategory, Integer count) {
		if (shopCategory == null || shopCategory.getParent() == null) {
			return Collections.<ShopCategory> emptyList();
		}
		String jpql = "select shopCategory from ShopCategory shopCategory where shopCategory.id in (:ids) order by shopCategory.grade asc";
		TypedQuery<ShopCategory> query = entityManager.createQuery(jpql, ShopCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("ids", shopCategory.getTreePaths());
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ShopCategory> findChildren(ShopCategory shopCategory, Integer count) {
		TypedQuery<ShopCategory> query;
		if (shopCategory != null) {
			String jpql = "select shopCategory from ShopCategory shopCategory where shopCategory.treePath like :treePath order by shopCategory.order asc";
			query = entityManager.createQuery(jpql, ShopCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%" + ShopCategory.TREE_PATH_SEPARATOR + shopCategory.getId() + ShopCategory.TREE_PATH_SEPARATOR + "%");
		} else {
			String jpql = "select shopCategory from ShopCategory shopCategory order by shopCategory.order asc";
			query = entityManager.createQuery(jpql, ShopCategory.class).setFlushMode(FlushModeType.COMMIT);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return sort(query.getResultList(), shopCategory);
	}

	/**
	 * 设置treePath、grade并保存
	 * 
	 * @param shopCategory
	 *            店铺分类
	 */
	@Override
	public void persist(ShopCategory shopCategory) {
		Assert.notNull(shopCategory);
		setValue(shopCategory);
		super.persist(shopCategory);
	}

	/**
	 * 设置treePath、grade并更新
	 * 
	 * @param shopCategory
	 *            店铺分类
	 * @return 店铺分类
	 */
	@Override
	public ShopCategory merge(ShopCategory shopCategory) {
		Assert.notNull(shopCategory);
		setValue(shopCategory);
		for (ShopCategory category : findChildren(shopCategory, null)) {
			setValue(category);
		}
		return super.merge(shopCategory);
	}

	/**
	 * 排序店铺分类
	 * 
	 * @param shopCategories
	 *            店铺分类
	 * @param parent
	 *            上级店铺分类
	 * @return 店铺分类
	 */
	private List<ShopCategory> sort(List<ShopCategory> shopCategories, ShopCategory parent) {
		List<ShopCategory> result = new ArrayList<ShopCategory>();
		if (shopCategories != null) {
			for (ShopCategory shopCategory : shopCategories) {
				if ((shopCategory.getParent() != null && shopCategory.getParent().equals(parent)) || (shopCategory.getParent() == null && parent == null)) {
					result.add(shopCategory);
					result.addAll(sort(shopCategories, shopCategory));
				}
			}
		}
		return result;
	}

	/**
	 * 设置值
	 * 
	 * @param shopCategory
	 *            店铺分类
	 */
	private void setValue(ShopCategory shopCategory) {
		if (shopCategory == null) {
			return;
		}
		ShopCategory parent = shopCategory.getParent();
		if (parent != null) {
			shopCategory.setTreePath(parent.getTreePath() + parent.getId() + ShopCategory.TREE_PATH_SEPARATOR);
		} else {
			shopCategory.setTreePath(ShopCategory.TREE_PATH_SEPARATOR);
		}
		shopCategory.setGrade(shopCategory.getTreePaths().size());
	}

}