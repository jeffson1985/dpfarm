/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dp2345.Principal;
import com.dp2345.Setting;
import com.dp2345.dao.DepositDao;
import com.dp2345.dao.MemberDao;
import com.dp2345.dao.ShopDao;
import com.dp2345.entity.Admin;
import com.dp2345.entity.Member;
import com.dp2345.entity.Shop;
import com.dp2345.service.ShopService;
import com.dp2345.util.SettingUtils;

/**
 * Service - 店铺
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("shopServiceImpl")
public class ShopServiceImpl extends BaseServiceImpl<Shop, Long> implements ShopService {

	@Resource(name = "shopDaoImpl")
	private ShopDao shopDao;
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "depositDaoImpl")
	private DepositDao depositDao;

	@Resource(name = "shopDaoImpl")
	public void setBaseDao(ShopDao shopDao) {
		super.setBaseDao(shopDao);
	}
	

	@Transactional(readOnly = true)
	public boolean shopAliasExists(String shopAlias) {
		return shopDao.shopAliasExists(shopAlias);
	}

	@Transactional(readOnly = true)
	public boolean shopAliasDisabled(String shopAlias) {
		Assert.hasText(shopAlias);
		Setting setting = SettingUtils.get();
		if (setting.getDisabledUsernames() != null) {
			for (String disabledUsername : setting.getDisabledUsernames()) {
				if (StringUtils.containsIgnoreCase(shopAlias, disabledUsername)) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return shopDao.emailExists(email);
	}

	@Transactional(readOnly = true)
	public boolean emailUnique(String previousEmail, String currentEmail) {
		if (StringUtils.equalsIgnoreCase(previousEmail, currentEmail)) {
			return true;
		} else {
			if (shopDao.emailExists(currentEmail)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public void save(Shop shop, Admin operator) {
		Assert.notNull(shop);
		shopDao.persist(shop);
		/*
		if (shop.getBalance().compareTo(new BigDecimal(0)) > 0) {
			Deposit deposit = new Deposit();
			deposit.setType(operator != null ? Deposit.Type.adminRecharge : Deposit.Type.shopRecharge);
			deposit.setCredit(shop.getBalance());
			deposit.setDebit(new BigDecimal(0));
			deposit.setBalance(shop.getBalance());
			deposit.setOperator(operator != null ? operator.getUsername() : null);
			deposit.setShop(shop);
			depositDao.persist(deposit);
			
		}
		*/
	}

	public void update(Shop shop, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo, Admin operator) {
		Assert.notNull(shop);

		shopDao.lock(shop, LockModeType.PESSIMISTIC_WRITE);

		if (modifyPoint != null && modifyPoint != 0 && shop.getPoint() + modifyPoint >= 0) {
			shop.setPoint(shop.getPoint() + modifyPoint);
		}
/*
		if (modifyBalance != null && modifyBalance.compareTo(new BigDecimal(0)) != 0 && shop.getBalance().add(modifyBalance).compareTo(new BigDecimal(0)) >= 0) {
			shop.setBalance(shop.getBalance().add(modifyBalance));
			Deposit deposit = new Deposit();
			if (modifyBalance.compareTo(new BigDecimal(0)) > 0) {
				deposit.setType(operator != null ? Deposit.Type.adminRecharge : Deposit.Type.shopRecharge);
				deposit.setCredit(modifyBalance);
				deposit.setDebit(new BigDecimal(0));
			} else {
				deposit.setType(operator != null ? Deposit.Type.adminChargeback : Deposit.Type.shopPayment);
				deposit.setCredit(new BigDecimal(0));
				deposit.setDebit(modifyBalance);
			}
			deposit.setBalance(shop.getBalance());
			deposit.setOperator(operator != null ? operator.getUsername() : null);
			deposit.setMemo(depositMemo);
			deposit.setShop(shop);
			depositDao.persist(deposit);
		}
		*/
		shopDao.merge(shop);
	}

	@Transactional(readOnly = true)
	public Shop findByshopAlias(String shopAlias) {
		return shopDao.findByShopAlias(shopAlias);
	}

	@Transactional(readOnly = true)
	public List<Shop> findListByEmail(String email) {
		return shopDao.findListByEmail(email);
	}

	@Transactional(readOnly = true)
	public List<Object[]> findSellList(Date beginDate, Date endDate, Integer count) {
		return shopDao.findSellList(beginDate, endDate, count);
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 取得店主信息
	 */
	@Transactional(readOnly = true)
	public Member getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return memberDao.find(principal.getId());
			}
		}
		return null;
	}

	/**
	 * 取得店主用户名
	 */
	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	
}