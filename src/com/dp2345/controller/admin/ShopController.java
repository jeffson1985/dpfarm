/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.CommonAttributes;
import com.dp2345.Message;
import com.dp2345.Pageable;
import com.dp2345.Setting;
import com.dp2345.entity.Area;
import com.dp2345.entity.BaseEntity.Save;
import com.dp2345.entity.Shop;
import com.dp2345.entity.ShopAttribute;
import com.dp2345.entity.ShopAttribute.Type;
import com.dp2345.service.AdminService;
import com.dp2345.service.AreaService;
import com.dp2345.service.ShopAttributeService;
import com.dp2345.service.ShopRankService;
import com.dp2345.service.ShopService;
import com.dp2345.util.SettingUtils;

/**
 * Controller - 店铺
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController extends BaseController {

	@Resource(name = "shopServiceImpl")
	private ShopService shopService;
	@Resource(name = "shopRankServiceImpl")
	private ShopRankService shopRankService;
	@Resource(name = "shopAttributeServiceImpl")
	private ShopAttributeService shopAttributeService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (shopService.shopAliasDisabled(username) || shopService.shopAliasExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查E-mail是否唯一
	 */
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkEmail(String previousEmail, String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		if (shopService.emailUnique(previousEmail, email)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		model.addAttribute("shopAttributes", shopAttributeService.findList());
		model.addAttribute("shop", shopService.find(id));
		return "/admin/shop/view";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		//model.addAttribute("genders", Gender.values());
		model.addAttribute("shopRanks", shopRankService.findAll());
		model.addAttribute("shopAttributes", shopAttributeService.findList());
		return "/admin/shop/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Shop shop, Long shopRankId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		shop.setShopRank(shopRankService.find(shopRankId));
		if (!isValid(shop, Save.class)) {
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		if (shop.getShopName().length() < setting.getUsernameMinLength() || shop.getShopName().length() > setting.getUsernameMaxLength()) {
			return ERROR_VIEW;
		}
		if (shop.getShopAlias().length() < setting.getUsernameMinLength() || shop.getShopAlias().length() > setting.getUsernameMaxLength()) {
			return ERROR_VIEW;
		}
		
		if (shopService.shopAliasDisabled(shop.getShopAlias()) || shopService.shopAliasExists(shop.getShopAlias())) {
			return ERROR_VIEW;
		}
		if (!setting.getIsDuplicateEmail() && shopService.emailExists(shop.getEmail())) {
			return ERROR_VIEW;
		}
		shop.removeAttributeValue();
		for (ShopAttribute shopAttribute : shopAttributeService.findList()) {
			String parameter = request.getParameter("shopAttribute_" + shopAttribute.getId());
			if (shopAttribute.getType() == Type.name || shopAttribute.getType() == Type.address || shopAttribute.getType() == Type.zipCode || shopAttribute.getType() == Type.phone || shopAttribute.getType() == Type.mobile || shopAttribute.getType() == Type.text || shopAttribute.getType() == Type.select) {
				if (shopAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
					return ERROR_VIEW;
				}
				shop.setAttributeValue(shopAttribute, parameter);
			}else if (shopAttribute.getType() == Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter) ? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (shopAttribute.getIsRequired() && birth == null) {
						return ERROR_VIEW;
					}
					shop.setBirth(birth);
				} catch (ParseException e) {
					return ERROR_VIEW;
				}
			} else if (shopAttribute.getType() == Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					shop.setArea(area);
				} else if (shopAttribute.getIsRequired()) {
					return ERROR_VIEW;
				}
			} else if (shopAttribute.getType() == Type.checkbox) {
				String[] parameterValues = request.getParameterValues("shopAttribute_" + shopAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (shopAttribute.getIsRequired() && (options == null || options.isEmpty())) {
					return ERROR_VIEW;
				}
				shop.setAttributeValue(shopAttribute, options);
			}
		}
		shop.setShopName(shop.getShopName().toLowerCase());
		shop.setShopAlias(shop.getShopAlias().toLowerCase());
		shop.setAmount(new BigDecimal(0));
		shop.setIsLocked(false);
		shop.setLockedDate(null);
		shop.setOrders(null);
		shop.setPayments(null);
		shop.setReviews(null);
		shop.setConsultations(null);
		shop.setInMessages(null);
		shop.setOutMessages(null);
		shopService.save(shop, adminService.getCurrent());
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		
		model.addAttribute("shopRanks", shopRankService.findAll());
		model.addAttribute("shopAttributes", shopAttributeService.findList());
		model.addAttribute("shop", shopService.find(id));
		return "/admin/shop/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Shop shop, Long shopRankId, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		shop.setShopRank(shopRankService.find(shopRankId));
		if (!isValid(shop)) {
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		
		Shop pShop = shopService.find(shop.getId());
		if (pShop == null) {
			return ERROR_VIEW;
		}
		if (!setting.getIsDuplicateEmail() && !shopService.emailUnique(pShop.getEmail(), shop.getEmail())) {
			return ERROR_VIEW;
		}
		shop.removeAttributeValue();
		for (ShopAttribute shopAttribute : shopAttributeService.findList()) {
			String parameter = request.getParameter("shopAttribute_" + shopAttribute.getId());
			if (shopAttribute.getType() == Type.name || shopAttribute.getType() == Type.address || shopAttribute.getType() == Type.zipCode || shopAttribute.getType() == Type.phone || shopAttribute.getType() == Type.mobile || shopAttribute.getType() == Type.text || shopAttribute.getType() == Type.select) {
				if (shopAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
					return ERROR_VIEW;
				}
				shop.setAttributeValue(shopAttribute, parameter);
			}else if (shopAttribute.getType() == Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter) ? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (shopAttribute.getIsRequired() && birth == null) {
						return ERROR_VIEW;
					}
					shop.setBirth(birth);
				} catch (ParseException e) {
					return ERROR_VIEW;
				}
			} else if (shopAttribute.getType() == Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					shop.setArea(area);
				} else if (shopAttribute.getIsRequired()) {
					return ERROR_VIEW;
				}
			} else if (shopAttribute.getType() == Type.checkbox) {
				String[] parameterValues = request.getParameterValues("shopAttribute_" + shopAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (shopAttribute.getIsRequired() && (options == null || options.isEmpty())) {
					return ERROR_VIEW;
				}
				shop.setAttributeValue(shopAttribute, options);
			}
		}

		BeanUtils.copyProperties(shop, pShop, new String[] { "username", "point", "amount", "balance", "registerIp", "loginIp", "loginDate", "safeKey", "cart", "orders", "deposits", "payments", "couponCodes", "receivers", "reviews", "consultations", "favoriteProducts", "productNotifies", "inMessages", "outMessages" });
		shopService.update(pShop, modifyPoint, modifyBalance, depositMemo, adminService.getCurrent());
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("shopRanks", shopRankService.findAll());
		model.addAttribute("shopAttributes", shopAttributeService.findAll());
		model.addAttribute("page", shopService.findPage(pageable));
		return "/admin/shop/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Shop shop = shopService.find(id);
				// 店铺尚且存在商品，无法删除
				if (shop != null && shop.getProducts().size() > 0) {
					return Message.error("admin.shop.deleteExistDepositNotAllowed", shop.getShopName());
				}
			}
			shopService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}