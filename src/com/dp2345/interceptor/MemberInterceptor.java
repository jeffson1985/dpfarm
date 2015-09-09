/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.interceptor;

import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dp2345.Principal;
import com.dp2345.entity.Member;
import com.dp2345.service.MemberService;

/**
 * Interceptor - 会员权限
 * 会员登陆与否拦截器
 * @author CETVISION CORP
 * @version 2.0.3
 */
public class MemberInterceptor extends HandlerInterceptorAdapter {

	/** 重定向视图名称前缀 */
	private static final String REDIRECT_VIEW_NAME_PREFIX = "redirect:";

	/** "重定向URL"参数名称 */
	private static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";

	/** "会员"属性名称 */
	private static final String MEMBER_ATTRIBUTE_NAME = "member";

	/** 默认登录URL */
	private static final String DEFAULT_LOGIN_URL = "/login.jhtml";

	/** 登录URL */
	private String loginUrl = DEFAULT_LOGIN_URL;

	@Value("${url_escaping_charset}")
	private String urlEscapingCharset;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	// 会员登陆与否判断
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
		if (principal != null) {
			return true;
		} else {
			// ajax请求判断
			String requestType = request.getHeader("X-Requested-With");
			if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
				// ajax请求
				response.addHeader("loginStatus", "accessDenied");
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return false;
			} else {
				// 普通请求 GET
				if (request.getMethod().equalsIgnoreCase("GET")) {
					String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
					response.sendRedirect(request.getContextPath() + loginUrl + "?" + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl, urlEscapingCharset));
				} else {
					// 普通请求 POST
					response.sendRedirect(request.getContextPath() + loginUrl);
				}
				return false;
			}
		}
	}

	// 如果会员登陆，自动向各个页面添加会员信息
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			if (!StringUtils.startsWith(viewName, REDIRECT_VIEW_NAME_PREFIX)) {
				modelAndView.addObject(MEMBER_ATTRIBUTE_NAME, memberService.getCurrent());
			}
		}
	}

	/**
	 * 获取登录URL
	 * 
	 * @return 登录URL
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * 设置登录URL
	 * 
	 * @param loginUrl
	 *            登录URL
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

}