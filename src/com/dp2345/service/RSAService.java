/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;

/**
 * Service - RSA安全
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface RSAService {

	/**
	 * 生成密钥(添加私钥至Session并返回公钥)
	 * 
	 * @param request
	 *            httpServletRequest
	 * @return 公钥
	 */
	RSAPublicKey generateKey(HttpServletRequest request);

	/**
	 * 移除私钥
	 * 
	 * @param request
	 *            httpServletRequest
	 */
	void removePrivateKey(HttpServletRequest request);

	/**
	 * 解密参数
	 * 
	 * @param name
	 *            参数名称
	 * @param request
	 *            httpServletRequest
	 * @return 解密内容
	 */
	String decryptParameter(String name, HttpServletRequest request);

}