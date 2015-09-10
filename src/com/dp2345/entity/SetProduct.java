/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.entity;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang.StringUtils;
import org.dom4j.io.SAXReader;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.io.ClassPathResource;

import com.dp2345.BigDecimalNumericFieldBridge;
import com.dp2345.CommonAttributes;
import com.dp2345.util.FreemarkerUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import freemarker.template.TemplateException;

/**
 * Entity - 套餐商品
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Entity
@Table(name = "xx_set_product")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_set_product_sequence")
public class SetProduct extends OrderEntity {

	private static final long serialVersionUID = 3536993535267962279L;
	
	/** 静态路径 */
	private static String staticPath;

	/**
	 * 排序类型
	 */
	public enum OrderType {

		/** 置顶降序 */
		topDesc,

		/** 价格升序 */
		priceAsc,

		/** 价格降序 */
		priceDesc,

		/** 销量降序 */
		salesDesc,

		/** 评分降序 */
		scoreDesc,

		/** 日期降序 */
		dateDesc
	}

	/** 编号 */
	private String sn;

	/** 名称 */
	private String name;

	/** 标题 */
	private String title;
	
	/** 销售价 */
	private BigDecimal price;
	
	/** 赠送积分 */
	private Long point;

	/** 展示图片 */
	private String image;

	/** 起始日期 */
	private Date beginDate;

	/** 结束日期 */
	private Date endDate;

	/** 积分运算表达式 */
	private String pointExpression;

	/** 是否免运费 */
	private Boolean isFreeShipping;

	/** 是否允许使用优惠券 */
	private Boolean isCouponAllowed;

	/** 介绍 */
	private String introduction;

	/** 参与商品 */
	private Set<SetProductItem> setProductItems = new HashSet<SetProductItem>();
	
	/** 所属店铺 */
	private Shop shop;

	/** 赠送优惠券 */
	private Set<Coupon> coupons = new HashSet<Coupon>();
	
	/** 会员价 */
	private Map<MemberRank, BigDecimal> memberPrice = new HashMap<MemberRank, BigDecimal>();

	/** 单位 */
	private String unit;
	
	
	static {
		try {
			File dp2345XmlFile = new ClassPathResource(CommonAttributes.DP2345_XML_PATH).getFile();
			org.dom4j.Document document = new SAXReader().read(dp2345XmlFile);
			org.dom4j.Element element = (org.dom4j.Element) document.selectSingleNode("/dp2345/template[@id='setProductContent']");
			staticPath = element.attributeValue("staticPath");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@JsonProperty
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	@JsonProperty
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取起始日期
	 * 
	 * @return 起始日期
	 */
	@JsonProperty
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置起始日期
	 * 
	 * @param beginDate
	 *            起始日期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取结束日期
	 * 
	 * @return 结束日期
	 */
	@JsonProperty
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束日期
	 * 
	 * @param endDate
	 *            结束日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 获取积分运算表达式
	 * 
	 * @return 积分运算表达式
	 */
	public String getPointExpression() {
		return pointExpression;
	}

	/**
	 * 设置积分运算表达式
	 * 
	 * @param pointExpression
	 *            积分运算表达式
	 */
	public void setPointExpression(String pointExpression) {
		this.pointExpression = pointExpression;
	}

	/**
	 * 获取是否免运费
	 * 
	 * @return 是否免运费
	 */
	@NotNull
	@Column(nullable = false)
	public Boolean getIsFreeShipping() {
		return isFreeShipping;
	}

	/**
	 * 设置是否免运费
	 * 
	 * @param isFreeShipping
	 *            是否免运费
	 */
	public void setIsFreeShipping(Boolean isFreeShipping) {
		this.isFreeShipping = isFreeShipping;
	}

	/**
	 * 获取是否允许使用优惠券
	 * 
	 * @return 是否允许使用优惠券
	 */
	@JsonProperty
	@NotNull
	@Column(nullable = false)
	public Boolean getIsCouponAllowed() {
		return isCouponAllowed;
	}

	/**
	 * 设置是否允许使用优惠券
	 * 
	 * @param isCouponAllowed
	 *            是否允许使用优惠券
	 */
	public void setIsCouponAllowed(Boolean isCouponAllowed) {
		this.isCouponAllowed = isCouponAllowed;
	}

	/**
	 * 获取介绍
	 * 
	 * @return 介绍
	 */
	@Lob
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 设置介绍
	 * 
	 * @param introduction
	 *            介绍
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@OneToMany(mappedBy = "set_product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<SetProductItem> getSetProductItems() {
		return setProductItems;
	}

	/**
	 * 设置允许参与商品
	 * 
	 * @param products
	 *            允许参与商品
	 */
	public void setSetProductItems(Set<SetProductItem> setProductItems) {
		this.setProductItems = setProductItems;
	}

	/**
	 * 获取所属店铺
	 * 
	 * @return 所属店铺
	 */	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Shop getShop() {
		return shop;
	}

	/**
	 * 设置所属店铺
	 * 
	 * @param shop
	 *            所属店铺
	 */
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	

	/**
	 * 获取赠送优惠券
	 * 
	 * @return 赠送优惠券
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_promotion_coupon")
	public Set<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * 设置赠送优惠券
	 * 
	 * @param coupons
	 *            赠送优惠券
	 */
	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	
	/**
	 * 获取编号
	 * 
	 * @return 编号
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$")
	@Length(max = 100)
	@Column(nullable = false, unique = true, length = 100)
	public String getSn() {
		return sn;
	}

	/**
	 * 设置编号
	 * 
	 * @param sn
	 *            编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Field(store = Store.YES, index = Index.NO)
	@Min(0)
	@Column(nullable = false)
	public Long getPoint() {
		return point;
	}

	/**
	 * 设置赠送积分
	 * 
	 * @param point
	 *            赠送积分
	 */
	public void setPoint(Long point) {
		this.point = point;
	}
	
	/**
	 * 获取销售价
	 * 
	 * @return 销售价
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	@NumericField
	@FieldBridge(impl = BigDecimalNumericFieldBridge.class)
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置销售价
	 * 
	 * @param price
	 *            销售价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/**
	 * 获取展示图片
	 * 
	 * @return 展示图片
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.NO)
	@Length(max = 200)
	public String getImage() {
		return image;
	}

	/**
	 * 设置展示图片
	 * 
	 * @param image
	 *            展示图片
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * 获取单位
	 * 
	 * @return 单位
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.NO)
	@Length(max = 200)
	public String getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 *            单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	/**
	 * 获取会员价
	 * 
	 * @return 会员价
	 */
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "xx_product_member_price")
	public Map<MemberRank, BigDecimal> getMemberPrice() {
		return memberPrice;
	}

	/**
	 * 设置会员价
	 * 
	 * @param memberPrice
	 *            会员价
	 */
	public void setMemberPrice(Map<MemberRank, BigDecimal> memberPrice) {
		this.memberPrice = memberPrice;
	}
	
	// 以下未非持久化项目

	/**
	 * 判断是否已开始
	 * 
	 * @return 是否已开始
	 */
	@Transient
	public boolean hasBegun() {
		return getBeginDate() == null || new Date().after(getBeginDate());
	}

	/**
	 * 判断是否已结束
	 * 
	 * @return 是否已结束
	 */
	@Transient
	public boolean hasEnded() {
		return getEndDate() != null && new Date().after(getEndDate());
	}

	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", getId());
		model.put("createDate", getCreateDate());
		model.put("modifyDate", getModifyDate());
		model.put("sn", getSn());
		model.put("name", getName());
		model.put("fullName", getTitle());
		try {
			return FreemarkerUtils.process(staticPath, model);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算套餐商品重量
	 * 
	 * @return 套餐商品重量
	 */
	@Transient
	public Integer getWeight() {
		
		Integer result = 0;
		Set<SetProductItem> items = getSetProductItems();
		for(SetProductItem item: items){
			result += item.getWeight();
			
		}
		return result;
	}

	/**
	 * 计算套餐赠送积分
	 * 
	 * @return 套餐赠送积分
	 */
	@Transient
	public Long calculatePoint() {
		Long point = 0L;
		if(getSetProductItems() != null){
			for(SetProductItem item: getSetProductItems()){
				point += item.getSubPoint();
			}
		}
		if (StringUtils.isEmpty(getPointExpression())) {
			return point;
		}
		Long result = 0L;
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("quantity", 1);
			model.put("point", point);
			result = Double.valueOf(FreemarkerUtils.process("#{(" + getPointExpression() + ");M50}", model)).longValue();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (result < point) {
			return point;
		}
		return result > 0L ? result : 0L;
	}
	
	
	/**
	 * 计算原本价格
	 * 
	 * @return 商品原本价格
	 */
	@Transient
	public BigDecimal calculatePrice() {
		BigDecimal price = new BigDecimal(0);
		if (getSetProductItems() != null) {
			for (SetProductItem item : getSetProductItems()) {
				if (item != null && item.getSubPrice() != null) {
					price = price.add(item.getSubPrice());
				}
			}
		}
		return price;
	}
	
	/**
	 * 获取成本价格
	 * 
	 * @return 成本价格
	 */
	@Transient
	public BigDecimal getCost() {
		BigDecimal cost = new BigDecimal(0);
		if (getSetProductItems() != null) {
			for (SetProductItem item : getSetProductItems()) {
				if (item != null && item.getSubCost() != null) {
					cost = cost.add(item.getSubCost());
				}
			}
		}
		return cost;
	}


	/**
	 * 获取可用库存
	 * 
	 * @return 可用库存
	 */
	@Transient
	public Integer getAvailableStock() {
		Integer tempStock = null;
		Integer availableStock = 10000;
		if(getSetProductItems() != null){
			for(SetProductItem item: getSetProductItems()){
				tempStock = (item.getProduct().getStock()-item.getProduct().getAllocatedStock())/item.getQuantity();
				if(availableStock > tempStock)
					availableStock = tempStock;
			}
		}
		return availableStock < 0 ? 0: availableStock;
	}

	/**
	 * 获取是否缺货
	 * 
	 * @return 是否缺货
	 */
	@Transient
	public Boolean getIsOutOfStock() {
		boolean isOutOfStock = false;
		if(getSetProductItems() != null){
			for(SetProductItem item: getSetProductItems()){
				Product product = item.getProduct();
				if(product.getStock() == null || product.getAllocatedStock() >= product.getStock())
					isOutOfStock = true;
			}
		}
		return isOutOfStock;
	}
}