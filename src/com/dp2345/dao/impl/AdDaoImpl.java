/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.AdDao;
import com.dp2345.entity.Ad;

/**
 * Dao - 广告
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("adDaoImpl")
public class AdDaoImpl extends BaseDaoImpl<Ad, Long> implements AdDao {

}