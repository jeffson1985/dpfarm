/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dp2345.entity.ProductCategory;
import com.dp2345.service.ProductCategoryService;
import com.dp2345.util.FreemarkerUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 上级商品分类列表
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Component("productCategoryParentListDirective")
public class ProductCategoryParentListDirective extends BaseDirective {

	/** "商品分类ID"参数名称 */
	private static final String PRODUCT_CATEGORY_ID_PARAMETER_NAME = "productCategoryId";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "productCategories";

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Long productCategoryId = FreemarkerUtils.getParameter(PRODUCT_CATEGORY_ID_PARAMETER_NAME, Long.class, params);

		ProductCategory productCategory = productCategoryService.find(productCategoryId);

		List<ProductCategory> productCategories;
		if (productCategoryId != null && productCategory == null) {
			productCategories = new ArrayList<ProductCategory>();
		} else {
			boolean useCache = useCache(env, params);
			String cacheRegion = getCacheRegion(env, params);
			Integer count = getCount(params);
			if (useCache) {
				productCategories = productCategoryService.findParents(productCategory, count, cacheRegion);
			} else {
				productCategories = productCategoryService.findParents(productCategory, count);
			}
		}
		setLocalVariable(VARIABLE_NAME, productCategories, env, body);
	}

}