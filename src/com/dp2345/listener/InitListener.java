/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.listener;

import java.io.File;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.dp2345.service.CacheService;
import com.dp2345.service.SearchService;
import com.dp2345.service.StaticService;

/**
 * Listener - 初始化
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Component("initListener")
public class InitListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

	/** 安装初始化配置文件 */
	private static final String INSTALL_INIT_CONFIG_FILE_PATH = "/install_init.conf";

	/** logger */
	private static final Logger logger = Logger.getLogger(InitListener.class.getName());

	/** servletContext */
	private ServletContext servletContext;

	@Value("${system.version}")
	private String systemVersion;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (servletContext != null && contextRefreshedEvent.getApplicationContext().getParent() == null) {
			String info = "I|n|i|t|i|a|l|i|z|i|n|g| |D|P|2|3|4|5| |" + systemVersion;
			logger.info(info.replace("|", ""));
			File installInitConfigFile = new File(servletContext.getRealPath(INSTALL_INIT_CONFIG_FILE_PATH));
			if (installInitConfigFile.exists()) {
				cacheService.clear();
				staticService.buildAll();
				searchService.purge();
				searchService.index();
				installInitConfigFile.delete();
			} else {
				staticService.buildIndex();
				staticService.buildOther();
			}
		}
	}

}