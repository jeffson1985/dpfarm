/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dp2345.entity.ShopAttribute;
import com.dp2345.service.ShopAttributeService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 会员注册项列表
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Component("shopAttributeListDirective")
public class ShopAttributeListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "shopAttributes";

	@Resource(name = "shopAttributeServiceImpl")
	private ShopAttributeService shopAttributeService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<ShopAttribute> shopAttributes;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		if (useCache) {
			shopAttributes = shopAttributeService.findList(cacheRegion);
		} else {
			shopAttributes = shopAttributeService.findList();
		}
		setLocalVariable(VARIABLE_NAME, shopAttributes, env, body);
	}

}