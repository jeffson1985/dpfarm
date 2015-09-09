/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.template.method;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.dp2345.Setting;
import com.dp2345.util.SettingUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 模板方法 - 货币格式化
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Component("currencyMethod")
public class CurrencyMethod implements TemplateMethodModel {

	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null && StringUtils.isNotEmpty(arguments.get(0).toString())) {
			boolean showSign = false;
			boolean showUnit = false;
			if (arguments.size() == 2) {
				if (arguments.get(1) != null) {
					showSign = Boolean.valueOf(arguments.get(1).toString());
				}
			} else if (arguments.size() > 2) {
				if (arguments.get(1) != null) {
					showSign = Boolean.valueOf(arguments.get(1).toString());
				}
				if (arguments.get(2) != null) {
					showUnit = Boolean.valueOf(arguments.get(2).toString());
				}
			}
			Setting setting = SettingUtils.get();
			BigDecimal amount = new BigDecimal(arguments.get(0).toString());
			String price = setting.setScale(amount).toString();
			if (showSign) {
				price = setting.getCurrencySign() + price;
			}
			if (showUnit) {
				price += setting.getCurrencyUnit();
			}
			return new SimpleScalar(price);
		}
		return null;
	}

}