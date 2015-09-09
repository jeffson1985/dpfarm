/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.RoleDao;
import com.dp2345.entity.Role;

/**
 * Dao - 角色
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {

}