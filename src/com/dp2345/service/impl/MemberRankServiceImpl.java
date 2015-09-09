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

import com.dp2345.dao.MemberRankDao;
import com.dp2345.entity.MemberRank;
import com.dp2345.service.MemberRankService;

/**
 * Service - 会员等级
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("memberRankServiceImpl")
public class MemberRankServiceImpl extends BaseServiceImpl<MemberRank, Long> implements MemberRankService {

	@Resource(name = "memberRankDaoImpl")
	private MemberRankDao memberRankDao;

	@Resource(name = "memberRankDaoImpl")
	public void setBaseDao(MemberRankDao memberRankDao) {
		super.setBaseDao(memberRankDao);
	}

	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return memberRankDao.nameExists(name);
	}

	@Transactional(readOnly = true)
	public boolean nameUnique(String previousName, String currentName) {
		if (StringUtils.equalsIgnoreCase(previousName, currentName)) {
			return true;
		} else {
			if (memberRankDao.nameExists(currentName)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Transactional(readOnly = true)
	public boolean amountExists(BigDecimal amount) {
		return memberRankDao.amountExists(amount);
	}

	@Transactional(readOnly = true)
	public boolean amountUnique(BigDecimal previousAmount, BigDecimal currentAmount) {
		if (previousAmount != null && previousAmount.compareTo(currentAmount) == 0) {
			return true;
		} else {
			if (memberRankDao.amountExists(currentAmount)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Transactional(readOnly = true)
	public MemberRank findDefault() {
		return memberRankDao.findDefault();
	}

	@Transactional(readOnly = true)
	public MemberRank findByAmount(BigDecimal amount) {
		return memberRankDao.findByAmount(amount);
	}

}