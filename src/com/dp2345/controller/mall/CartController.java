/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.mall;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dp2345.Message;
import com.dp2345.entity.Cart;
import com.dp2345.entity.CartItem;
import com.dp2345.entity.Member;
import com.dp2345.entity.Product;
import com.dp2345.service.CartItemService;
import com.dp2345.service.CartService;
import com.dp2345.service.MemberService;
import com.dp2345.service.ProductService;
import com.dp2345.util.WebUtils;

/**
 * Controller - 购物车
 * 原理
 * 数据库 ＋ cookie保存方式，避免使用session 
 * 未完成， 当有套餐商品时，如何处理
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("shopCartController")
@RequestMapping("/cart")
public class CartController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody
	Message add(Long id, Integer quantity, HttpServletRequest request, HttpServletResponse response) {
		if (quantity == null || quantity < 1) {
			return ERROR_MESSAGE;
		}
		Product product = productService.find(id);
		if (product == null) {
			return Message.warn("shop.cart.productNotExsit");
		}
		if (!product.getIsMarketable()) {
			return Message.warn("shop.cart.productNotMarketable");
		}
		if (product.getIsGift()) {
			return Message.warn("shop.cart.notForSale");
		}
		// 获取当前购物车
		Cart cart = cartService.getCurrent();
		// 获取当前会员
		Member member = memberService.getCurrent();
		// 如果购物车为空，新规
		if (cart == null) {
			cart = new Cart();
			cart.setKey(UUID.randomUUID().toString() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(30)));
			cart.setMember(member);
			cartService.save(cart);
		}

		// 判断购物车里面商品的数量是否超荷
		if (Cart.MAX_PRODUCT_COUNT != null && cart.getCartItems().size() >= Cart.MAX_PRODUCT_COUNT) {
			return Message.warn("shop.cart.addCountNotAllowed", Cart.MAX_PRODUCT_COUNT);
		}

		//如果购物车里已经存在此商品
		if (cart.contains(product)) {
			CartItem cartItem = cart.getCartItem(product);
			// 判断此商品最大购买数量是否超荷
			if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() + quantity > CartItem.MAX_QUANTITY) {
				return Message.warn("shop.cart.maxCartItemQuantity", CartItem.MAX_QUANTITY);
			}
			// 判断库存
			if (product.getStock() != null && cartItem.getQuantity() + quantity > product.getAvailableStock()) {
				return Message.warn("shop.cart.productLowStock");
			}
			// 增加数量
			cartItem.add(quantity);
		    // 保存到数据库
			cartItemService.update(cartItem);
		} else {
			// 新商品，判断最大购买数量
			if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
				return Message.warn("shop.cart.maxCartItemQuantity", CartItem.MAX_QUANTITY);
			}
			// 判断库存
			if (product.getStock() != null && quantity > product.getAvailableStock()) {
				return Message.warn("shop.cart.productLowStock");
			}
			// 购物车对象
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItemService.save(cartItem);
			cart.getCartItems().add(cartItem);
		}

		// 如果会员没有登陆，那么暂时把购物车信息放到cookie里面
		if (member == null) {
			WebUtils.addCookie(request, response, Cart.ID_COOKIE_NAME, cart.getId().toString(), Cart.TIMEOUT);
			WebUtils.addCookie(request, response, Cart.KEY_COOKIE_NAME, cart.getKey(), Cart.TIMEOUT);
		}
		// 返回添加成功信息
		return Message.success("shop.cart.addSuccess", cart.getQuantity(), currency(cart.getEffectivePrice(), true, false));
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("cart", cartService.getCurrent());
		return "/shop/cart/list";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> edit(Long id, Integer quantity) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (quantity == null || quantity < 1) {
			data.put("message", ERROR_MESSAGE);
			return data;
		}
		Cart cart = cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			data.put("message", Message.error("shop.cart.notEmpty"));
			return data;
		}
		CartItem cartItem = cartItemService.find(id);
		Set<CartItem> cartItems = cart.getCartItems();
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			data.put("message", Message.error("shop.cart.cartItemNotExsit"));
			return data;
		}
		if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
			data.put("message", Message.warn("shop.cart.maxCartItemQuantity", CartItem.MAX_QUANTITY));
			return data;
		}
		Product product = cartItem.getProduct();
		if (product.getStock() != null && quantity > product.getAvailableStock()) {
			data.put("message", Message.warn("shop.cart.productLowStock"));
			return data;
		}
		cartItem.setQuantity(quantity);
		cartItemService.update(cartItem);

		data.put("message", SUCCESS_MESSAGE);
		data.put("subtotal", cartItem.getSubtotal());
		data.put("isLowStock", cartItem.getIsLowStock());
		data.put("quantity", cart.getQuantity());
		data.put("effectivePoint", cart.getEffectivePoint());
		data.put("effectivePrice", cart.getEffectivePrice());
		data.put("promotions", cart.getPromotions());
		data.put("giftItems", cart.getGiftItems());
		return data;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> delete(Long id) {
		Map<String, Object> data = new HashMap<String, Object>();
		Cart cart = cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			data.put("message", Message.error("shop.cart.notEmpty"));
			return data;
		}
		CartItem cartItem = cartItemService.find(id);
		Set<CartItem> cartItems = cart.getCartItems();
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			data.put("message", Message.error("shop.cart.cartItemNotExsit"));
			return data;
		}
		cartItems.remove(cartItem);
		cartItemService.delete(cartItem);

		data.put("message", SUCCESS_MESSAGE);
		data.put("quantity", cart.getQuantity());
		data.put("effectivePoint", cart.getEffectivePoint());
		data.put("effectivePrice", cart.getEffectivePrice());
		data.put("promotions", cart.getPromotions());
		data.put("isLowStock", cart.getIsLowStock());
		return data;
	}

	/**
	 * 清空
	 */
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	public @ResponseBody
	Message clear() {
		Cart cart = cartService.getCurrent();
		cartService.delete(cart);
		return SUCCESS_MESSAGE;
	}

}