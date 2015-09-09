/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import java.util.List;

import com.dp2345.entity.Admin;

/**
 * Service - 管理员
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface AdminService extends BaseService<Admin, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Admin findByUsername(String username);

	/**
	 * 根据ID查找权限
	 * 
	 * @param id
	 *            ID
	 * @return 权限,若不存在则返回null
	 */
	List<String> findAuthorities(Long id);

	/**
	 * 判断管理员是否登录
	 * 
	 * @return 管理员是否登录
	 */
	boolean isAuthenticated();

	/**
	 * 获取当前登录管理员
	 * 
	 * @return 当前登录管理员,若不存在则返回null
	 */
	Admin getCurrent();

	/**
	 * 获取当前登录用户名
	 * 
	 * @return 当前登录用户名,若不存在则返回null
	 */
	String getCurrentUsername();

}