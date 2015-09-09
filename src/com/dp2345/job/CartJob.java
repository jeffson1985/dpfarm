/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.job;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dp2345.service.CartService;

/**
 * Job - 购物车
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Component("cartJob")
@Lazy(false)
public class CartJob {

	@Resource(name = "cartServiceImpl")
	private CartService cartService;

	/**
	 * 清除过期购物车
	 */
	@Scheduled(cron = "${job.cart_evict_expired.cron}")
	public void evictExpired() {
		cartService.evictExpired();
	}

}