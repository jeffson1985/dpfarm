/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.Message;
import com.dp2345.Pageable;
import com.dp2345.entity.Review;
import com.dp2345.entity.Review.Type;
import com.dp2345.service.ReviewService;

/**
 * Controller - 评论
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminReviewController")
@RequestMapping("/admin/review")
public class ReviewController extends BaseController {

	@Resource(name = "reviewServiceImpl")
	private ReviewService reviewService;

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("review", reviewService.find(id));
		return "/admin/review/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Long id, @RequestParam(defaultValue = "false") Boolean isShow, RedirectAttributes redirectAttributes) {
		Review review = reviewService.find(id);
		if (review == null) {
			return ERROR_VIEW;
		}
		review.setIsShow(isShow);
		reviewService.update(review);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Type type, Pageable pageable, ModelMap model) {
		model.addAttribute("type", type);
		model.addAttribute("types", Type.values());
		model.addAttribute("page", reviewService.findPage(null, null, type, null, pageable));
		return "/admin/review/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		reviewService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}