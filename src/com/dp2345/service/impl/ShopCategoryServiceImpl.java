/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dp2345.dao.ShopCategoryDao;
import com.dp2345.entity.ShopCategory;
import com.dp2345.service.ShopCategoryService;

/**
 * Service - 店铺分类
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("shopCategoryServiceImpl")
public class ShopCategoryServiceImpl extends BaseServiceImpl<ShopCategory, Long> implements ShopCategoryService {

	@Resource(name = "shopCategoryDaoImpl")
	private ShopCategoryDao shopCategoryDao;

	@Resource(name = "shopCategoryDaoImpl")
	public void setBaseDao(ShopCategoryDao shopCategoryDao) {
		super.setBaseDao(shopCategoryDao);
	}

	@Transactional(readOnly = true)
	public List<ShopCategory> findRoots() {
		return shopCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<ShopCategory> findRoots(Integer count) {
		return shopCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable("shopCategory")
	public List<ShopCategory> findRoots(Integer count, String cacheRegion) {
		return shopCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<ShopCategory> findParents(ShopCategory shopCategory) {
		return shopCategoryDao.findParents(shopCategory, null);
	}

	@Transactional(readOnly = true)
	public List<ShopCategory> findParents(ShopCategory shopCategory, Integer count) {
		return shopCategoryDao.findParents(shopCategory, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("shopCategory")
	public List<ShopCategory> findParents(ShopCategory shopCategory, Integer count, String cacheRegion) {
		return shopCategoryDao.findParents(shopCategory, count);
	}

	@Transactional(readOnly = true)
	public List<ShopCategory> findTree() {
		return shopCategoryDao.findChildren(null, null);
	}

	@Transactional(readOnly = true)
	public List<ShopCategory> findChildren(ShopCategory shopCategory) {
		return shopCategoryDao.findChildren(shopCategory, null);
	}

	@Transactional(readOnly = true)
	public List<ShopCategory> findChildren(ShopCategory shopCategory, Integer count) {
		return shopCategoryDao.findChildren(shopCategory, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("shopCategory")
	public List<ShopCategory> findChildren(ShopCategory shopCategory, Integer count, String cacheRegion) {
		return shopCategoryDao.findChildren(shopCategory, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "shop", "shopCategory" }, allEntries = true)
	public void save(ShopCategory shopCategory) {
		super.save(shopCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "shop", "shopCategory" }, allEntries = true)
	public ShopCategory update(ShopCategory shopCategory) {
		return super.update(shopCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "shop", "shopCategory" }, allEntries = true)
	public ShopCategory update(ShopCategory shopCategory, String... ignoreProperties) {
		return super.update(shopCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "shop", "shopCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "shop", "shopCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "shop", "shopCategory" }, allEntries = true)
	public void delete(ShopCategory shopCategory) {
		super.delete(shopCategory);
	}

}