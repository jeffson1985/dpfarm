/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao;

import java.util.List;

import com.dp2345.entity.Tag;
import com.dp2345.entity.Tag.Type;

/**
 * Dao - 标签
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public interface TagDao extends BaseDao<Tag, Long> {

	/**
	 * 查找标签
	 * 
	 * @param type
	 *            类型
	 * @return 标签
	 */
	List<Tag> findList(Type type);

}