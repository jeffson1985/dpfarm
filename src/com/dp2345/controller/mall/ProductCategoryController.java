/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.mall;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dp2345.service.ProductCategoryService;

/**
 * Controller - 商品分类
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("shopProductCategoryController")
@RequestMapping("/product_category")
public class ProductCategoryController extends BaseController {

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

	/**
	 * 首页
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model) {
		model.addAttribute("rootProductCategories", productCategoryService.findRoots());
		return "/shop/product_category/index";
	}

}