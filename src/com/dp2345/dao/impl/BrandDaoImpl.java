/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.BrandDao;
import com.dp2345.entity.Brand;

/**
 * Dao - 品牌
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("brandDaoImpl")
public class BrandDaoImpl extends BaseDaoImpl<Brand, Long> implements BrandDao {

}