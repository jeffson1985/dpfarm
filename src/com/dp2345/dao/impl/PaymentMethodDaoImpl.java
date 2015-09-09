/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.dao.impl;

import org.springframework.stereotype.Repository;

import com.dp2345.dao.PaymentMethodDao;
import com.dp2345.entity.PaymentMethod;

/**
 * Dao - 支付方式
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Repository("paymentMethodDaoImpl")
public class PaymentMethodDaoImpl extends BaseDaoImpl<PaymentMethod, Long> implements PaymentMethodDao {

}