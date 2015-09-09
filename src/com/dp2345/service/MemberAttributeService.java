/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service;

import java.util.List;

import com.dp2345.entity.MemberAttribute;

/**
 * Service - 会员注册项
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface MemberAttributeService extends BaseService<MemberAttribute, Long> {

	/**
	 * 查找未使用的对象属性序号
	 * 
	 * @return 未使用的对象属性序号，若无可用序号则返回null
	 */
	Integer findUnusedPropertyIndex();

	/**
	 * 查找会员注册项
	 * 
	 * @return 会员注册项，仅包含已启用会员注册项
	 */
	List<MemberAttribute> findList();

	/**
	 * 查找会员注册项(缓存)
	 * 
	 * @param cacheRegion
	 *            缓存区域
	 * @return 会员注册项(缓存)，仅包含已启用会员注册项
	 */
	List<MemberAttribute> findList(String cacheRegion);

}