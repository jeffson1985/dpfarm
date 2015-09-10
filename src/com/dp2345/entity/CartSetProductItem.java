/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.entity;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dp2345.Setting;
import com.dp2345.util.SettingUtils;

/**
 * Entity - 购物车项
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Entity
@Table(name = "xx_cart_set_product_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_cart_set_product_item_sequence")
public class CartSetProductItem extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3094372721086923939L;
	

	/** 最大数量 */
	public static final Integer MAX_QUANTITY = 100;

	/** 数量 */
	private Integer quantity;

	/** 商品 */
	private SetProduct setProduct;

	/** 购物车 */
	private Cart cart;

	/** 临时商品价格 */
	private BigDecimal tempPrice;

	/** 临时赠送积分 */
	private Long tempPoint;

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
	public SetProduct getSetProduct() {
		return setProduct;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setSetProduct(SetProduct setProduct) {
		this.setProduct = setProduct;
	}

	/**
	 * 获取购物车
	 * 
	 * @return 购物车
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Cart getCart() {
		return cart;
	}

	/**
	 * 设置购物车
	 * 
	 * @param cart
	 *            购物车
	 */
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	/**
	 * 获取临时商品价格
	 * 
	 * @return 临时商品价格
	 */
	@Transient
	public BigDecimal getTempPrice() {
		if (tempPrice == null) {
			return getSubtotal();
		}
		return tempPrice;
	}

	/**
	 * 设置临时商品价格
	 * 
	 * @param tempPrice
	 *            临时商品价格
	 */
	@Transient
	public void setTempPrice(BigDecimal tempPrice) {
		this.tempPrice = tempPrice;
	}

	/**
	 * 获取临时赠送积分
	 * 
	 * @return 临时赠送积分
	 */
	@Transient
	public Long getTempPoint() {
		if (tempPoint == null) {
			return getPoint();
		}
		return tempPoint;
	}

	/**
	 * 设置临时赠送积分
	 * 
	 * @param tempPoint
	 *            临时赠送积分
	 */
	@Transient
	public void setTempPoint(Long tempPoint) {
		this.tempPoint = tempPoint;
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Transient
	public long getPoint() {
		if (getSetProduct() != null && getSetProduct().getPoint() != null && getQuantity() != null) {
			return getSetProduct().getPoint() * getQuantity();
		} else {
			return 0L;
		}
	}

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		if (getSetProduct() != null && getSetProduct().getWeight() != null && getQuantity() != null) {
			return getSetProduct().getWeight() * getQuantity();
		} else {
			return 0;
		}
	}

	/**
	 * 获取价格
	 * 
	 * @return 价格
	 */
	@Transient
	public BigDecimal getPrice() {
		if (getSetProduct() != null && getSetProduct().getPrice() != null) {
			Setting setting = SettingUtils.get();
			if (getCart() != null && getCart().getMember() != null && getCart().getMember().getMemberRank() != null) {
				MemberRank memberRank = getCart().getMember().getMemberRank();
				Map<MemberRank, BigDecimal> memberPrice = getSetProduct().getMemberPrice();
				if (memberPrice != null && !memberPrice.isEmpty()) {
					if (memberPrice.containsKey(memberRank)) {
						return setting.setScale(memberPrice.get(memberRank));
					}
				}
				if (memberRank.getScale() != null) {
					return setting.setScale(getSetProduct().getPrice().multiply(new BigDecimal(memberRank.getScale())));
				}
			}
			return setting.setScale(getSetProduct().getPrice());
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@Transient
	public BigDecimal getSubtotal() {
		if (getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * 获取是否库存不足
	 * 
	 * @return 是否库存不足
	 */
	@Transient
	public boolean getIsLowStock() {
		if (getQuantity() != null && getSetProduct() != null && !getSetProduct().getIsOutOfStock() && getQuantity() > getSetProduct().getAvailableStock()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 增加商品数量
	 * 
	 * @param quantity
	 *            数量
	 */
	@Transient
	public void add(int quantity) {
		if (quantity > 0) {
			if (getQuantity() != null) {
				setQuantity(getQuantity() + quantity);
			} else {
				setQuantity(quantity);
			}
		}
	}

}