/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dp2345.dao.RefundsDao;
import com.dp2345.entity.Refunds;
import com.dp2345.service.RefundsService;

/**
 * Service - 退款单
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("refundsServiceImpl")
public class RefundsServiceImpl extends BaseServiceImpl<Refunds, Long> implements RefundsService {

	@Resource(name = "refundsDaoImpl")
	public void setBaseDao(RefundsDao refundsDao) {
		super.setBaseDao(refundsDao);
	}

}