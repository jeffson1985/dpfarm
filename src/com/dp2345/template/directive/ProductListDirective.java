/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.template.directive;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dp2345.Filter;
import com.dp2345.Order;
import com.dp2345.entity.Area;
import com.dp2345.entity.Article;
import com.dp2345.entity.Attribute;
import com.dp2345.entity.Brand;
import com.dp2345.entity.Product;
import com.dp2345.entity.ProductCategory;
import com.dp2345.entity.Promotion;
import com.dp2345.entity.Shop;
import com.dp2345.entity.Tag;
import com.dp2345.entity.Product.OrderType;
import com.dp2345.service.AreaService;
import com.dp2345.service.AttributeService;
import com.dp2345.service.BrandService;
import com.dp2345.service.ProductCategoryService;
import com.dp2345.service.ProductService;
import com.dp2345.service.PromotionService;
import com.dp2345.service.ShopService;
import com.dp2345.service.TagService;
import com.dp2345.util.FreemarkerUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 商品列表
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Component("productListDirective")
public class ProductListDirective extends BaseDirective {

	/** "商品分类ID"参数名称 */
	private static final String PRODUCT_CATEGORY_ID_PARAMETER_NAME = "productCategoryId";

	/** "品牌ID"参数名称 */
	private static final String BRAND_ID_PARAMETER_NAME = "brandId";

	/** "促销ID"参数名称 */
	private static final String PROMOTION_ID_PARAMETER_NAME = "promotionId";

	/** "标签ID"参数名称 */
	private static final String TAG_IDS_PARAMETER_NAME = "tagIds";

	/** "属性值"参数名称 */
	private static final String ATTRIBUTE_VALUE_PARAMETER_NAME = "attributeValue";

	/** "最低价格"参数名称 */
	private static final String START_PRICE_PARAMETER_NAME = "startPrice";

	/** "最高价格"参数名称 */
	private static final String END_PRICE_PARAMETER_NAME = "endPrice";

	/** "排序类型"参数名称 */
	private static final String ORDER_TYPE_PARAMETER_NAME = "orderType";
	
	/** "商品地区"参数名  */
	private static final String AREA_ID_PARAMETER_NAME = "areaId";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "products";

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "attributeServiceImpl")
	private AttributeService attributeService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "shopServiceImpl")
	private ShopService shopService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Long productCategoryId = FreemarkerUtils.getParameter(PRODUCT_CATEGORY_ID_PARAMETER_NAME, Long.class, params);
		Long brandId = FreemarkerUtils.getParameter(BRAND_ID_PARAMETER_NAME, Long.class, params);
		Long promotionId = FreemarkerUtils.getParameter(PROMOTION_ID_PARAMETER_NAME, Long.class, params);
		Long[] tagIds = FreemarkerUtils.getParameter(TAG_IDS_PARAMETER_NAME, Long[].class, params);
		Map<Long, String> attributeValue = FreemarkerUtils.getParameter(ATTRIBUTE_VALUE_PARAMETER_NAME, Map.class, params);
		BigDecimal startPrice = FreemarkerUtils.getParameter(START_PRICE_PARAMETER_NAME, BigDecimal.class, params);
		BigDecimal endPrice = FreemarkerUtils.getParameter(END_PRICE_PARAMETER_NAME, BigDecimal.class, params);
		OrderType orderType = FreemarkerUtils.getParameter(ORDER_TYPE_PARAMETER_NAME, OrderType.class, params);
		
		Long areaId = FreemarkerUtils.getParameter(AREA_ID_PARAMETER_NAME, Long.class, params);
		Set<Shop> shops = null;
		if(areaId != null){
			Area currentArea = areaService.find(areaId);
			Set<Area> childArea = currentArea.getChildren();
			/* 根据店铺地域查找相应的商品*/
			shops = currentArea.getshops();
			if(shops == null || shops.size() == 0){
				shops = new HashSet<Shop>();
				for(Area area : childArea){
					shops.addAll(area.getshops());
				}
			}
		}
		
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagIds);
		Map<Attribute, String> attributeValueMap = new HashMap<Attribute, String>();
		if (attributeValue != null) {
			for (Entry<Long, String> entry : attributeValue.entrySet()) {
				Attribute attribute = attributeService.find(entry.getKey());
				if (attribute != null) {
					attributeValueMap.put(attribute, entry.getValue());
				}
			}
		}

		List<Product> products;
		if ((productCategoryId != null && productCategory == null) || (brandId != null && brand == null) || (promotionId != null && promotion == null) || (tagIds != null && tags.isEmpty()) || (attributeValue != null && attributeValueMap.isEmpty())) {
			products = new ArrayList<Product>();
		} else {
			boolean useCache = useCache(env, params);
			String cacheRegion = getCacheRegion(env, params);
			Integer count = getCount(params);
			List<Filter> filters = getFilters(params, Article.class);
			List<Order> orders = getOrders(params);
			if (useCache) {
				products = productService.findList(productCategory, brand, promotion, tags, attributeValueMap, startPrice, endPrice, true, true, null, false, null, null, orderType, count, filters, orders, cacheRegion);
			} else {
				products = productService.findList(productCategory, brand, promotion, tags, attributeValueMap, startPrice, endPrice, true, true, null, false, null, null, orderType, count, filters, orders);
			}
		}
		setLocalVariable(VARIABLE_NAME, products, env, body);
	}

}