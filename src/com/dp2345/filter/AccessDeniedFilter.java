/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;


/**
 * Filter - 限制访问
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public class AccessDeniedFilter implements Filter {

	/** 错误消息 */
	private static final String ERROR_MESSAGE = "Access denied!";

	private Base64 base64 = new Base64();
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// PoweredBy   dp2345.com
		response.addHeader(new String(base64.decode("UG93ZXJlZEJ5"), "utf-8"), new String(base64.decode("ZHAyMzQ1LmNvbQ=="), "utf-8"));
		response.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_MESSAGE);
	}

}