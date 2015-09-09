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

import com.dp2345.Filter;
import com.dp2345.Order;
import com.dp2345.dao.FriendLinkDao;
import com.dp2345.entity.FriendLink;
import com.dp2345.entity.FriendLink.Type;
import com.dp2345.service.FriendLinkService;

/**
 * Service - 友情链接
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("friendLinkServiceImpl")
public class FriendLinkServiceImpl extends BaseServiceImpl<FriendLink, Long> implements FriendLinkService {

	@Resource(name = "friendLinkDaoImpl")
	public FriendLinkDao friendLinkDao;

	@Resource(name = "friendLinkDaoImpl")
	public void setBaseDao(FriendLinkDao friendLinkDao) {
		super.setBaseDao(friendLinkDao);
	}

	@Transactional(readOnly = true)
	public List<FriendLink> findList(Type type) {
		return friendLinkDao.findList(type);
	}

	@Transactional(readOnly = true)
	@Cacheable("friendLink")
	public List<FriendLink> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion) {
		return friendLinkDao.findList(null, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public void save(FriendLink friendLink) {
		super.save(friendLink);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public FriendLink update(FriendLink friendLink) {
		return super.update(friendLink);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public FriendLink update(FriendLink friendLink, String... ignoreProperties) {
		return super.update(friendLink, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public void delete(FriendLink friendLink) {
		super.delete(friendLink);
	}

}