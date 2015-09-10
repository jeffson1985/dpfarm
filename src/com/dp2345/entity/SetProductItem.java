/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity - 套餐商品明细
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Entity
@Table(name = "xx_set_product_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_set_product_item_sequence")
public class SetProductItem extends OrderEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5683108355657546850L;
	
	/** 商品 */
	private Product product;
	
	/** 数量 */
	private Integer quantity;

	/** 套餐 */
	private SetProduct  setProduct;

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@Column(nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置数量
	 * 
	 * @param quantity
	 *            数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取套餐
	 * 
	 * @return套餐
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public SetProduct getSetProduct() {
		return setProduct;
	}

	/**
	 * 设置套餐
	 * 
	 * @param setProduct
	 *            套餐
	 */
	public void setSetProduct(SetProduct setProduct) {
		this.setProduct = setProduct;
	}
	
	
	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		if (getProduct() != null && getProduct().getWeight() != null && getQuantity() != null) {
			return getProduct().getWeight() * getQuantity();
		} else {
			return 0;
		}
	}
	
	/**
	 * 获取小计价格
	 * 
	 * @return 小计价格
	 */
	@Transient
	public BigDecimal getSubPrice() {
		if (getProduct() != null && getProduct().getPrice() != null) {
			
			BigDecimal subTotal = getProduct().getPrice().multiply(new BigDecimal(getQuantity()));
			
			return subTotal;
		} else {
			return new BigDecimal(0);
		}
	}
	
	/**
	 * 获取小计成本
	 * 
	 * @return 小计成本
	 */
	@Transient
	public BigDecimal getSubCost() {
		if (getProduct() != null && getProduct().getCost() != null) {
			
			BigDecimal subCost = getProduct().getCost().multiply(new BigDecimal(getQuantity()));
			
			return subCost;
		} else {
			return new BigDecimal(0);
		}
	}
	
	/**
	 * 获取小计积分
	 * 
	 * @return 小计积分
	 */
	@Transient
	public Long getSubPoint() {
		if (getProduct() != null && getProduct().getPrice() != null) {
			
			Long subTotal = getProduct().getPoint() * getQuantity();
			
			return subTotal;
		} else {
			return 0L;
		}
	}
	
	
	
}