/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.Message;
import com.dp2345.Pageable;
import com.dp2345.entity.ShopRank;
import com.dp2345.service.ShopRankService;

/**
 * Controller - 店铺等级
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminShopRankController")
@RequestMapping("/admin/shop_rank")
public class ShopRankController extends BaseController {

	@Resource(name = "shopRankServiceImpl")
	private ShopRankService shopRankService;

	/**
	 * 检查名称是否唯一
	 */
	@RequestMapping(value = "/check_name", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkName(String previousName, String name) {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		if (shopRankService.nameUnique(previousName, name)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查消费金额是否唯一
	 */
	@RequestMapping(value = "/check_amount", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkAmount(BigDecimal previousAmount, BigDecimal amount) {
		if (amount == null) {
			return false;
		}
		if (shopRankService.amountUnique(previousAmount, amount)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/shop_rank/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ShopRank shopRank, RedirectAttributes redirectAttributes) {
		if (!isValid(shopRank)) {
			return ERROR_VIEW;
		}
		if (shopRankService.nameExists(shopRank.getName())) {
			return ERROR_VIEW;
		}
		if (shopRank.getIsSpecial()) {
			shopRank.setAmount(null);
		} else if (shopRank.getAmount() == null || shopRankService.amountExists(shopRank.getAmount())) {
			return ERROR_VIEW;
		}
		shopRank.setShops(null);
		shopRank.setPromotions(null);
		shopRankService.save(shopRank);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("shopRank", shopRankService.find(id));
		return "/admin/shop_rank/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ShopRank shopRank, RedirectAttributes redirectAttributes) {
		if (!isValid(shopRank)) {
			return ERROR_VIEW;
		}
		ShopRank pShopRank = shopRankService.find(shopRank.getId());
		if (pShopRank == null) {
			return ERROR_VIEW;
		}
		if (!shopRankService.nameUnique(pShopRank.getName(), shopRank.getName())) {
			return ERROR_VIEW;
		}
		if (pShopRank.getIsDefault()) {
			shopRank.setIsDefault(true);
		}
		if (shopRank.getIsSpecial()) {
			shopRank.setAmount(null);
		} else if (shopRank.getAmount() == null || !shopRankService.amountUnique(pShopRank.getAmount(), shopRank.getAmount())) {
			return ERROR_VIEW;
		}
		shopRankService.update(shopRank, "shops", "promotions");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", shopRankService.findPage(pageable));
		return "/admin/shop_rank/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				ShopRank shopRank = shopRankService.find(id);
				if (shopRank != null && shopRank.getShops() != null && !shopRank.getShops().isEmpty()) {
					return Message.error("admin.shopRank.deleteExistNotAllowed", shopRank.getName());
				}
			}
			long totalCount = shopRankService.count();
			if (ids.length >= totalCount) {
				return Message.error("admin.common.deleteAllNotAllowed");
			}
			shopRankService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}