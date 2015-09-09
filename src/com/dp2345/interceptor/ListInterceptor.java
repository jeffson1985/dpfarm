/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dp2345.util.WebUtils;

/**
 * Interceptor - 列表查询
 * 拼凑URL查询参数 ？以后的内容
 * @author CETVISION CORP
 * @version 2.0.3
 */
public class ListInterceptor extends HandlerInterceptorAdapter {

	/** 重定向视图名称前缀 */
	private static final String REDIRECT_VIEW_NAME_PREFIX = "redirect:";

	/** 列表查询Cookie名称 */
	private static final String LIST_QUERY_COOKIE_NAME = "listQuery";

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null && modelAndView.isReference()) {
			String viewName = modelAndView.getViewName();
			if (StringUtils.startsWith(viewName, REDIRECT_VIEW_NAME_PREFIX)) {
				String listQuery = WebUtils.getCookie(request, LIST_QUERY_COOKIE_NAME);
				if (StringUtils.isNotEmpty(listQuery)) {
					if (StringUtils.startsWith(listQuery, "?")) {
						listQuery = listQuery.substring(1);
					}
					if (StringUtils.contains(viewName, "?")) {
						modelAndView.setViewName(viewName + "&" + listQuery);
					} else {
						modelAndView.setViewName(viewName + "?" + listQuery);
					}
					WebUtils.removeCookie(request, response, LIST_QUERY_COOKIE_NAME);
				}
			}
		}
	}

}