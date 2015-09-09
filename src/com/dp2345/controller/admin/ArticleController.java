/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import java.util.HashSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp2345.Message;
import com.dp2345.Pageable;
import com.dp2345.entity.Article;
import com.dp2345.entity.Tag;
import com.dp2345.entity.Tag.Type;
import com.dp2345.service.ArticleCategoryService;
import com.dp2345.service.ArticleService;
import com.dp2345.service.TagService;

/**
 * Controller - 文章
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminArticleController")
@RequestMapping("/admin/article")
public class ArticleController extends BaseController {

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("articleCategoryTree", articleCategoryService.findTree());
		model.addAttribute("tags", tagService.findList(Type.article));
		return "/admin/article/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Article article, Long articleCategoryId, Long[] tagIds, RedirectAttributes redirectAttributes) {
		article.setArticleCategory(articleCategoryService.find(articleCategoryId));
		article.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		if (!isValid(article)) {
			return ERROR_VIEW;
		}
		article.setHits(0L);
		article.setPageNumber(null);
		articleService.save(article);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("articleCategoryTree", articleCategoryService.findTree());
		model.addAttribute("tags", tagService.findList(Type.article));
		model.addAttribute("article", articleService.find(id));
		return "/admin/article/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Article article, Long articleCategoryId, Long[] tagIds, RedirectAttributes redirectAttributes) {
		article.setArticleCategory(articleCategoryService.find(articleCategoryId));
		article.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		if (!isValid(article)) {
			return ERROR_VIEW;
		}
		articleService.update(article, "hits", "pageNumber");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", articleService.findPage(pageable));
		return "/admin/article/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		articleService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}