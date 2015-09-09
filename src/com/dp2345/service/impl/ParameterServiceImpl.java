/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dp2345.dao.ParameterDao;
import com.dp2345.entity.Parameter;
import com.dp2345.service.ParameterService;

/**
 * Service - 参数
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("parameterServiceImpl")
public class ParameterServiceImpl extends BaseServiceImpl<Parameter, Long> implements ParameterService {

	@Resource(name = "parameterDaoImpl")
	public void setBaseDao(ParameterDao parameterDao) {
		super.setBaseDao(parameterDao);
	}

}