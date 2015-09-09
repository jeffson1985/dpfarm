/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.entity;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.dp2345.entity.ShopAttribute.Type;
import com.dp2345.util.JsonUtils;

/**
 * Entity - 店铺
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Entity
@Table(name = "xx_shop")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_shop_sequence")
public class Shop extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5573899039363144864L;

	/** 店铺名称*/
	private String shopName;
	
	/** 店铺别名 英文名称*/
	private String shopAlias;
	
	/**  店铺注册项值属性个数 */
	public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 10;
	
	/** 店铺注册项值属性名称前缀 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

	/** 积分 */
	private Long point;

	/** 卖出金额 */
	private BigDecimal amount;

	/** 是否启用 */
	private Boolean isEnabled;

	/** 是否锁定 */
	private Boolean isLocked;

	/** 锁定日期 */
	private Date lockedDate;

	/** 创建日期 */
	private Date birth;
	
	/** E-mail */
	private String email;

	/** 地址 */
	private String address;

	/** 邮编 */
	private String zipCode;

	/** 电话 */
	private String phone;

	/** 手机 */
	private String mobile;

	/** 店铺注册项值0 */
	private String attributeValue0;

	/** 店铺注册项值1 */
	private String attributeValue1;

	/** 店铺注册项值2 */
	private String attributeValue2;

	/** 店铺注册项值3 */
	private String attributeValue3;

	/** 店铺注册项值4 */
	private String attributeValue4;

	/** 店铺注册项值5 */
	private String attributeValue5;

	/** 店铺注册项值6 */
	private String attributeValue6;

	/** 店铺注册项值7 */
	private String attributeValue7;

	/** 店铺注册项值8 */
	private String attributeValue8;

	/** 店铺注册项值9 */
	private String attributeValue9;


	/** 地区 */
	private Area area;

	/** 店铺等级 */
	private ShopRank shopRank;

	/** 所属商家 */
	private Member member;

	/** 订单 */
	private Set<Order> orders = new HashSet<Order>();

	/** 收款单 */
	private Set<Payment> payments = new HashSet<Payment>();

	/** 评论 */
	private Set<Review> reviews = new HashSet<Review>();

	/** 咨询 */
	private Set<Consultation> consultations = new HashSet<Consultation>();

	/** 接收的消息 */
	private Set<Message> inMessages = new HashSet<Message>();

	/** 发送的消息 */
	private Set<Message> outMessages = new HashSet<Message>();
	
	// 多用户商城开发添加
	/** 商品 */
	private Set<Product> products = new HashSet<Product>();
	
	/** 店铺分类 **/
	private ShopCategory shopCategory;

	/** 商品分类 */
	private Set<ProductCategory> productCategories = new HashSet<ProductCategory>();

	/** 促销 */
	private Set<Promotion> promotions = new HashSet<Promotion>();
	
	/** 店铺文章 */
	private Set<Article> articles = new HashSet<Article>();
	
	/** 店铺地图经线坐标X */
	private String mapX;
	
	/** 店铺地图纬线坐标Y */
	private String mapY;
	
	/** 店铺URL */
	private String shopDomain;
	
	/** 店铺 logo */
	private String shopLogo;
	
	/** 店铺介绍照片 */
	private String shopImage;
	
	/** 店铺模版 */
	private String shopTemplate;
	
	/** 店铺模版照片 */
	private String shopTemplateImage;
	
	/** 店铺介绍 */
	private String shopDescript;
	
	/** 店铺商品数量*/
	private String productAmount;
	
	
	/**
	 * 获取店铺名称
	 * 
	 * @return 店铺名称
	 */
	@Length(max = 200)
	public String getShopName() {
		return shopName;
	}

	/**
	 * 设置店铺名称
	 * 
	 * @param storeName
	 *            店铺名称
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	/**
	 * 获取店铺别名
	 * 
	 * @return 店铺别名
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getShopAlias() {
		return shopAlias;
	}

	/**
	 * 设置店铺别名
	 * 
	 * @param storeAlias
	 *            店铺别名
	 */
	public void setShopAlias(String shopAlias) {
		this.shopAlias = shopAlias;
	}
	

	/**
	 * 获取E-mail
	 * 
	 * @return E-mail
	 */
	@NotEmpty
	@Email
	@Length(max = 200)
	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * 设置E-mail
	 * 
	 * @param email
	 *            E-mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取积分
	 * 
	 * @return 积分
	 */
	@NotNull(groups = Save.class)
	@Min(0)
	@Column(nullable = false)
	public Long getPoint() {
		return point;
	}

	/**
	 * 设置积分
	 * 
	 * @param point
	 *            积分
	 */
	public void setPoint(Long point) {
		this.point = point;
	}

	/**
	 * 获取消费金额
	 * 
	 * @return 消费金额
	 */
	@Column(nullable = false, precision = 27, scale = 12)
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置消费金额
	 * 
	 * @param amount
	 *            消费金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取是否启用
	 * 
	 * @return 是否启用
	 */
	@NotNull
	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * 设置是否启用
	 * 
	 * @param isEnabled
	 *            是否启用
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * 获取是否锁定
	 * 
	 * @return 是否锁定
	 */
	@Column(nullable = false)
	public Boolean getIsLocked() {
		return isLocked;
	}

	/**
	 * 设置是否锁定
	 * 
	 * @param isLocked
	 *            是否锁定
	 */
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}


	/**
	 * 获取锁定日期
	 * 
	 * @return 锁定日期
	 */
	public Date getLockedDate() {
		return lockedDate;
	}

	/**
	 * 设置锁定日期
	 * 
	 * @param lockedDate
	 *            锁定日期
	 */
	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	
	/**
	 * 获取创建日期
	 * 
	 * @return 创建日期
	 */
	public Date getBirth() {
		return birth;
	}

	/**
	 * 设置创建日期
	 * 
	 * @param birth
	 *            创建日期
	 */
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	/**
	 * 获取地址
	 * 
	 * @return 地址
	 */
	@Length(max = 200)
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 * 
	 * @param address
	 *            地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取邮编
	 * 
	 * @return 邮编
	 */
	@Length(max = 200)
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 * 
	 * @param zipCode
	 *            邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取电话
	 * 
	 * @return 电话
	 */
	@Length(max = 200)
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置电话
	 * 
	 * @param phone
	 *            电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取手机
	 * 
	 * @return 手机
	 */
	@Length(max = 200)
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 * 
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取店铺注册项值0
	 * 
	 * @return 店铺注册项值0
	 */
	@Length(max = 200)
	public String getAttributeValue0() {
		return attributeValue0;
	}

	/**
	 * 设置店铺注册项值0
	 * 
	 * @param attributeValue0
	 *            店铺注册项值0
	 */
	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	/**
	 * 获取店铺注册项值1
	 * 
	 * @return 店铺注册项值1
	 */
	@Length(max = 200)
	public String getAttributeValue1() {
		return attributeValue1;
	}

	/**
	 * 设置店铺注册项值1
	 * 
	 * @param attributeValue1
	 *            店铺注册项值1
	 */
	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	/**
	 * 获取店铺注册项值2
	 * 
	 * @return 店铺注册项值2
	 */
	@Length(max = 200)
	public String getAttributeValue2() {
		return attributeValue2;
	}

	/**
	 * 设置店铺注册项值2
	 * 
	 * @param attributeValue2
	 *            店铺注册项值2
	 */
	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	/**
	 * 获取店铺注册项值3
	 * 
	 * @return 店铺注册项值3
	 */
	@Length(max = 200)
	public String getAttributeValue3() {
		return attributeValue3;
	}

	/**
	 * 设置店铺注册项值3
	 * 
	 * @param attributeValue3
	 *            店铺注册项值3
	 */
	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	/**
	 * 获取店铺注册项值4
	 * 
	 * @return 店铺注册项值4
	 */
	@Length(max = 200)
	public String getAttributeValue4() {
		return attributeValue4;
	}

	/**
	 * 设置店铺注册项值4
	 * 
	 * @param attributeValue4
	 *            店铺注册项值4
	 */
	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	/**
	 * 获取店铺注册项值5
	 * 
	 * @return 店铺注册项值5
	 */
	@Length(max = 200)
	public String getAttributeValue5() {
		return attributeValue5;
	}

	/**
	 * 设置店铺注册项值5
	 * 
	 * @param attributeValue5
	 *            店铺注册项值5
	 */
	public void setAttributeValue5(String attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}

	/**
	 * 获取店铺注册项值6
	 * 
	 * @return 店铺注册项值6
	 */
	@Length(max = 200)
	public String getAttributeValue6() {
		return attributeValue6;
	}

	/**
	 * 设置店铺注册项值6
	 * 
	 * @param attributeValue6
	 *            店铺注册项值6
	 */
	public void setAttributeValue6(String attributeValue6) {
		this.attributeValue6 = attributeValue6;
	}

	/**
	 * 获取店铺注册项值7
	 * 
	 * @return 店铺注册项值7
	 */
	@Length(max = 200)
	public String getAttributeValue7() {
		return attributeValue7;
	}

	/**
	 * 设置店铺注册项值7
	 * 
	 * @param attributeValue7
	 *            店铺注册项值7
	 */
	public void setAttributeValue7(String attributeValue7) {
		this.attributeValue7 = attributeValue7;
	}

	/**
	 * 获取店铺注册项值8
	 * 
	 * @return 店铺注册项值8
	 */
	@Length(max = 200)
	public String getAttributeValue8() {
		return attributeValue8;
	}

	/**
	 * 设置店铺注册项值8
	 * 
	 * @param attributeValue8
	 *            店铺注册项值8
	 */
	public void setAttributeValue8(String attributeValue8) {
		this.attributeValue8 = attributeValue8;
	}

	/**
	 * 获取店铺注册项值9
	 * 
	 * @return 店铺注册项值9
	 */
	@Length(max = 200)
	public String getAttributeValue9() {
		return attributeValue9;
	}

	/**
	 * 设置店铺注册项值9
	 * 
	 * @param attributeValue9
	 *            店铺注册项值9
	 */
	public void setAttributeValue9(String attributeValue9) {
		this.attributeValue9 = attributeValue9;
	}

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 获取店铺等级
	 * 
	 * @return 店铺等级
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public ShopRank getShopRank() {
		return shopRank;
	}

	/**
	 * 设置店铺等级
	 * 
	 * @param storeRank
	 *            店铺等级
	 */
	public void setShopRank(ShopRank shopRank) {
		this.shopRank = shopRank;
	}

	/**
	 * 获取所属商家会员
	 * 一个商家可以拥有多家店铺
	 * 
	 * @return 购物车
	 */
	//@OneToOne(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置商家
	 * 
	 * @param member
	 *            商家
	 */
	public void setMember(Member member) {
		this.member = member;
	}
	
	/**
	 * 获取店铺分类
	 * 
	 * @return 店铺分类
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public ShopCategory getShopCategory() {
		return shopCategory;
	}

	/**
	 * 设置店铺分类
	 * 
	 * @param storeCategory
	 *            店铺分类
	 */
	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * 设置订单
	 * 
	 * @param orders
	 *            订单
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * 获取收款单
	 * 
	 * @return 收款单
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Payment> getPayments() {
		return payments;
	}

	/**
	 * 设置收款单
	 * 
	 * @param payments
	 *            收款单
	 */
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * 获取评论
	 * 
	 * @return 评论
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	public Set<Review> getReviews() {
		return reviews;
	}

	/**
	 * 设置评论
	 * 
	 * @param reviews
	 *            评论
	 */
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * 获取咨询
	 * 
	 * @return 咨询
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	public Set<Consultation> getConsultations() {
		return consultations;
	}

	/**
	 * 设置咨询
	 * 
	 * @param consultations
	 *            咨询
	 */
	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

	/**
	 * 获取接收的消息
	 * 
	 * @return 接收的消息
	 */
	@OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Message> getInMessages() {
		return inMessages;
	}

	/**
	 * 设置接收的消息
	 * 
	 * @param inMessages
	 *            接收的消息
	 */
	public void setInMessages(Set<Message> inMessages) {
		this.inMessages = inMessages;
	}

	/**
	 * 获取发送的消息
	 * 
	 * @return 发送的消息
	 */
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Message> getOutMessages() {
		return outMessages;
	}

	/**
	 * 设置发送的消息
	 * 
	 * @param outMessages
	 *            发送的消息
	 */
	public void setOutMessages(Set<Message> outMessages) {
		this.outMessages = outMessages;
	}
	
	
	
	// 多用户商城添加
	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * 设置商品
	 * 
	 * @param products
	 *            商品
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
	/**
	 * 获取文章
	 * 
	 * @return 文章
	 */
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	public Set<Article> getArticles() {
		return articles;
	}
	
	/**
	 * 设置文章
	 * 
	 * @param articles
	 *            文章
	 */
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	/**
	 * 获取商品分类
	 * 
	 * @return 商品分类
	 */
	//@ManyToMany(mappedBy = "stores", fetch = FetchType.LAZY)
	//@OrderBy("order asc")
	@OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
	public Set<ProductCategory> getProductCategories() {
		return productCategories;
	}

	/**
	 * 设置商品分类
	 * 
	 * @param productCategories
	 *            商品分类
	 */
	public void setProductCategories(Set<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}

	/**
	 * 获取促销
	 * 
	 * @return 促销
	 */
	@ManyToMany(mappedBy = "shops", fetch = FetchType.LAZY)
	public Set<Promotion> getPromotions() {
		return promotions;
	}

	/**
	 * 设置促销
	 * 
	 * @param promotions
	 *            促销
	 */
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}
	
	/**
	 * 获取经度
	 * 
	 * @return 经度
	 */
	public String getMapX() {
		return mapX;
	}


	/**
	 * 设置经度
	 * 
	 * @param promotions
	 *            经度
	 */
	public void setMapX(String mapX) {
		this.mapX = mapX;
	}

	/**
	 * 获取纬度
	 * 
	 * @return 纬度
	 */
	public String getMapY() {
		return mapY;
	}

	/**
	 * 设置纬度
	 * 
	 * @param mapY
	 *            纬度
	 */
	public void setMapY(String mapY) {
		this.mapY = mapY;
	}

	/**
	 * 获取店铺域名
	 * 
	 * @return 店铺域名
	 */
	public String getShopDomain() {
		return shopDomain;
	}

	/**
	 * 设置店铺域名
	 * 
	 * @param shopDomain
	 *            店铺域名
	 */
	public void setShopDomain(String shopDomain) {
		this.shopDomain = shopDomain;
	}

	/**
	 * 获取店铺logo
	 * 
	 * @return 店铺logo
	 */
	public String getShopLogo() {
		return shopLogo;
	}

	/**
	 * 设置店铺logo
	 * 
	 * @param shopLogo
	 *            店铺logo
	 */
	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	/**
	 * 获取店铺照片
	 * 
	 * @return 店铺照片
	 */
	public String getShopImage() {
		return shopImage;
	}

	/**
	 * 设置店铺照片
	 * 
	 * @param shopImage
	 *            店铺照片
	 */
	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}

	/**
	 * 获取店铺模版
	 * 
	 * @return 店铺模版
	 */
	public String getShopTemplate() {
		return shopTemplate;
	}

	/**
	 * 设置店铺模版
	 * 
	 * @param shopTemplate
	 *            店铺模版
	 */
	public void setShopTemplate(String shopTemplate) {
		this.shopTemplate = shopTemplate;
	}

	/**
	 * 获取模版照片
	 * 
	 * @return 模版照片
	 */
	public String getShopTemplateImage() {
		return shopTemplateImage;
	}

	/**
	 * 设置店铺模版照片
	 * 
	 * @param shopTemplateImage
	 *            模版照片
	 */
	public void setShopTemplateImage(String shopTemplateImage) {
		this.shopTemplateImage = shopTemplateImage;
	}

	/**
	 * 获取介绍
	 * 
	 * @return 介绍
	 */
	public String getShopDescript() {
		return shopDescript;
	}

	/**
	 * 设置店铺介绍
	 * 
	 * @param storeDescript
	 *            店铺介绍
	 */
	public void setShopDescript(String shopDescript) {
		this.shopDescript = shopDescript;
	}

	public String getProductAmount() {
		return productAmount;
	}

	/**
	 * 设置经度
	 * 
	 * @param promotions
	 *            经度
	 */
	public void setProductAmount(String productAmount) {
		this.productAmount = productAmount;
	}

	/**
	 * 获取店铺注册项值
	 * 
	 * @param storeAttribute
	 *            店铺注册项
	 * @return 店铺注册项值
	 */
	@Transient
	public Object getAttributeValue(ShopAttribute storeAttribute) {
		if (storeAttribute != null) {
			if (storeAttribute.getType() == Type.name) {
				return getShopName();
			} else if (storeAttribute.getType() == Type.area) {
				return getArea();
			} else if (storeAttribute.getType() == Type.address) {
				return getAddress();
			} else if (storeAttribute.getType() == Type.zipCode) {
				return getZipCode();
			} else if (storeAttribute.getType() == Type.phone) {
				return getPhone();
			} else if (storeAttribute.getType() == Type.mobile) {
				return getMobile();
			} else if (storeAttribute.getType() == Type.checkbox) {
				if (storeAttribute.getPropertyIndex() != null) {
					try {
						String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + storeAttribute.getPropertyIndex();
						String propertyValue = (String) PropertyUtils.getProperty(this, propertyName);
						if (propertyValue != null) {
							return JsonUtils.toObject(propertyValue, List.class);
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			} else {
				if (storeAttribute.getPropertyIndex() != null) {
					try {
						String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + storeAttribute.getPropertyIndex();
						return (String) PropertyUtils.getProperty(this, propertyName);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 设置店铺注册项值
	 * 
	 * @param storeAttribute
	 *            店铺注册项
	 * @param attributeValue
	 *            店铺注册项值
	 */
	@Transient
	public void setAttributeValue(ShopAttribute storeAttribute, Object attributeValue) {
		if (storeAttribute != null) {
			if (attributeValue instanceof String && StringUtils.isEmpty((String) attributeValue)) {
				attributeValue = null;
			}
			if (storeAttribute.getType() == Type.name && (attributeValue instanceof String || attributeValue == null)) {
				setShopName((String) attributeValue);
			}else if (storeAttribute.getType() == Type.birth && (attributeValue instanceof Date || attributeValue == null)) {
				setBirth((Date) attributeValue);
			} else if (storeAttribute.getType() == Type.area && (attributeValue instanceof Area || attributeValue == null)) {
				setArea((Area) attributeValue);
			} else if (storeAttribute.getType() == Type.address && (attributeValue instanceof String || attributeValue == null)) {
				setAddress((String) attributeValue);
			} else if (storeAttribute.getType() == Type.zipCode && (attributeValue instanceof String || attributeValue == null)) {
				setZipCode((String) attributeValue);
			} else if (storeAttribute.getType() == Type.phone && (attributeValue instanceof String || attributeValue == null)) {
				setPhone((String) attributeValue);
			} else if (storeAttribute.getType() == Type.mobile && (attributeValue instanceof String || attributeValue == null)) {
				setMobile((String) attributeValue);
			} else if (storeAttribute.getType() == Type.checkbox && (attributeValue instanceof List || attributeValue == null)) {
				if (storeAttribute.getPropertyIndex() != null) {
					if (attributeValue == null || (storeAttribute.getOptions() != null && storeAttribute.getOptions().containsAll((List<?>) attributeValue))) {
						try {
							String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + storeAttribute.getPropertyIndex();
							PropertyUtils.setProperty(this, propertyName, JsonUtils.toJson(attributeValue));
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				if (storeAttribute.getPropertyIndex() != null) {
					try {
						String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + storeAttribute.getPropertyIndex();
						PropertyUtils.setProperty(this, propertyName, attributeValue);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 移除所有店铺注册项值
	 */
	@Transient
	public void removeAttributeValue() {
		setBirth(null);
		setArea(null);
		setAddress(null);
		setZipCode(null);
		setPhone(null);
		setMobile(null);
		for (int i = 0; i < ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			try {
				PropertyUtils.setProperty(this, propertyName, null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Product> products = getProducts();
		if (products != null) {
			for (Product product : products) {
				product.setShop(null);
			}
		}
		/**
		// 商品分类删除操作
		Set<ProductCategory> productCategories = getProductCategories();
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				productCategory.getMembers().remove(this);
			}
		}
		// 商品促销删除操作
		Set<Promotion> promotions = getPromotions();
		if (promotions != null) {
			for (Promotion promotion : promotions) {
				promotion.getMembers().remove(this);
			}
		}
		**/
	}
}