/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dp2345.Page;
import com.dp2345.Pageable;
import com.dp2345.dao.CouponDao;
import com.dp2345.entity.Coupon;
import com.dp2345.service.CouponService;

/**
 * Service - 优惠券
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("couponServiceImpl")
public class CouponServiceImpl extends BaseServiceImpl<Coupon, Long> implements CouponService {

	@Resource(name = "couponDaoImpl")
	private CouponDao couponDao;

	@Resource(name = "couponDaoImpl")
	public void setBaseDao(CouponDao couponDao) {
		super.setBaseDao(couponDao);
	}

	@Transactional(readOnly = true)
	public Page<Coupon> findPage(Boolean isEnabled, Boolean isExchange, Boolean hasExpired, Pageable pageable) {
		return couponDao.findPage(isEnabled, isExchange, hasExpired, pageable);
	}

}