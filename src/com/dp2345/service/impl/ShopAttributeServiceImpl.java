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

import com.dp2345.dao.ShopAttributeDao;
import com.dp2345.entity.ShopAttribute;
import com.dp2345.service.ShopAttributeService;

/**
 * Service - 店铺注册项
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("shopAttributeServiceImpl")
public class ShopAttributeServiceImpl extends BaseServiceImpl<ShopAttribute, Long> implements ShopAttributeService {

	@Resource(name = "shopAttributeDaoImpl")
	private ShopAttributeDao shopAttributeDao;

	@Resource(name = "shopAttributeDaoImpl")
	public void setBaseDao(ShopAttributeDao shopAttributeDao) {
		super.setBaseDao(shopAttributeDao);
	}

	@Transactional(readOnly = true)
	public Integer findUnusedPropertyIndex() {
		return shopAttributeDao.findUnusedPropertyIndex();
	}

	@Transactional(readOnly = true)
	public List<ShopAttribute> findList() {
		return shopAttributeDao.findList();
	}

	@Transactional(readOnly = true)
	@Cacheable("shopAttribute")
	public List<ShopAttribute> findList(String cacheRegion) {
		return shopAttributeDao.findList();
	}

	@Override
	@Transactional
	@CacheEvict(value = "shopAttribute", allEntries = true)
	public void save(ShopAttribute shopAttribute) {
		super.save(shopAttribute);
	}

	@Override
	@Transactional
	@CacheEvict(value = "shopAttribute", allEntries = true)
	public ShopAttribute update(ShopAttribute shopAttribute) {
		return super.update(shopAttribute);
	}

	@Override
	@Transactional
	@CacheEvict(value = "shopAttribute", allEntries = true)
	public ShopAttribute update(ShopAttribute shopAttribute, String... ignoreProperties) {
		return super.update(shopAttribute, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "shopAttribute", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "shopAttribute", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "shopAttribute", allEntries = true)
	public void delete(ShopAttribute shopAttribute) {
		super.delete(shopAttribute);
	}

}