/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dp2345.dao.PluginConfigDao;
import com.dp2345.entity.PluginConfig;
import com.dp2345.service.PluginConfigService;

/**
 * Service - 插件配置
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("pluginConfigServiceImpl")
public class PluginConfigServiceImpl extends BaseServiceImpl<PluginConfig, Long> implements PluginConfigService {

	@Resource(name = "pluginConfigDaoImpl")
	private PluginConfigDao pluginConfigDao;

	@Resource(name = "pluginConfigDaoImpl")
	public void setBaseDao(PluginConfigDao pluginConfigDao) {
		super.setBaseDao(pluginConfigDao);
	}

	@Transactional(readOnly = true)
	public boolean pluginIdExists(String pluginId) {
		return pluginConfigDao.pluginIdExists(pluginId);
	}

	@Transactional(readOnly = true)
	public PluginConfig findByPluginId(String pluginId) {
		return pluginConfigDao.findByPluginId(pluginId);
	}

}