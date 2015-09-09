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
import com.dp2345.dao.ReceiverDao;
import com.dp2345.entity.Member;
import com.dp2345.entity.Receiver;
import com.dp2345.service.ReceiverService;

/**
 * Service - 收货地址
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("receiverServiceImpl")
public class ReceiverServiceImpl extends BaseServiceImpl<Receiver, Long> implements ReceiverService {

	@Resource(name = "receiverDaoImpl")
	private ReceiverDao receiverDao;

	@Resource(name = "receiverDaoImpl")
	public void setBaseDao(ReceiverDao receiverDao) {
		super.setBaseDao(receiverDao);
	}

	@Transactional(readOnly = true)
	public Receiver findDefault(Member member) {
		return receiverDao.findDefault(member);
	}

	@Transactional(readOnly = true)
	public Page<Receiver> findPage(Member member, Pageable pageable) {
		return receiverDao.findPage(member, pageable);
	}

}