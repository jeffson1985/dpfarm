/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dp2345.dao.ShopRankDao;
import com.dp2345.entity.ShopRank;
import com.dp2345.service.ShopRankService;

/**
 * Service - 店铺等级
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("shopRankServiceImpl")
public class ShopRankServiceImpl extends BaseServiceImpl<ShopRank, Long> implements ShopRankService {

	@Resource(name = "shopRankDaoImpl")
	private ShopRankDao shopRankDao;

	@Resource(name = "shopRankDaoImpl")
	public void setBaseDao(ShopRankDao shopRankDao) {
		super.setBaseDao(shopRankDao);
	}

	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return shopRankDao.nameExists(name);
	}

	@Transactional(readOnly = true)
	public boolean nameUnique(String previousName, String currentName) {
		if (StringUtils.equalsIgnoreCase(previousName, currentName)) {
			return true;
		} else {
			if (shopRankDao.nameExists(currentName)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Transactional(readOnly = true)
	public boolean amountExists(BigDecimal amount) {
		return shopRankDao.amountExists(amount);
	}

	@Transactional(readOnly = true)
	public boolean amountUnique(BigDecimal previousAmount, BigDecimal currentAmount) {
		if (previousAmount != null && previousAmount.compareTo(currentAmount) == 0) {
			return true;
		} else {
			if (shopRankDao.amountExists(currentAmount)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Transactional(readOnly = true)
	public ShopRank findDefault() {
		return shopRankDao.findDefault();
	}

	@Transactional(readOnly = true)
	public ShopRank findByAmount(BigDecimal amount) {
		return shopRankDao.findByAmount(amount);
	}

}