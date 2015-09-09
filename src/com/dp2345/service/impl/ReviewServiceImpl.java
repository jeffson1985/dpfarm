/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dp2345.Filter;
import com.dp2345.Order;
import com.dp2345.Page;
import com.dp2345.Pageable;
import com.dp2345.dao.ProductDao;
import com.dp2345.dao.ReviewDao;
import com.dp2345.entity.Member;
import com.dp2345.entity.Product;
import com.dp2345.entity.Review;
import com.dp2345.entity.Review.Type;
import com.dp2345.service.ReviewService;
import com.dp2345.service.StaticService;

/**
 * Service - 评论
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Service("reviewServiceImpl")
public class ReviewServiceImpl extends BaseServiceImpl<Review, Long> implements ReviewService {

	@Resource(name = "reviewDaoImpl")
	private ReviewDao reviewDao;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	@Resource(name = "reviewDaoImpl")
	public void setBaseDao(ReviewDao reviewDao) {
		super.setBaseDao(reviewDao);
	}

	@Transactional(readOnly = true)
	public List<Review> findList(Member member, Product product, Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		return reviewDao.findList(member, product, type, isShow, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable("review")
	public List<Review> findList(Member member, Product product, Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders, String cacheRegion) {
		return reviewDao.findList(member, product, type, isShow, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<Review> findPage(Member member, Product product, Type type, Boolean isShow, Pageable pageable) {
		return reviewDao.findPage(member, product, type, isShow, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member member, Product product, Type type, Boolean isShow) {
		return reviewDao.count(member, product, type, isShow);
	}

	@Transactional(readOnly = true)
	public boolean isReviewed(Member member, Product product) {
		return reviewDao.isReviewed(member, product);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void save(Review review) {
		super.save(review);
		Product product = review.getProduct();
		if (product != null) {
			reviewDao.flush();
			long totalScore = reviewDao.calculateTotalScore(product);
			long scoreCount = reviewDao.calculateScoreCount(product);
			product.setTotalScore(totalScore);
			product.setScoreCount(scoreCount);
			productDao.merge(product);
			reviewDao.flush();
			staticService.build(product);
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public Review update(Review review) {
		Review pReview = super.update(review);
		Product product = pReview.getProduct();
		if (product != null) {
			reviewDao.flush();
			long totalScore = reviewDao.calculateTotalScore(product);
			long scoreCount = reviewDao.calculateScoreCount(product);
			product.setTotalScore(totalScore);
			product.setScoreCount(scoreCount);
			productDao.merge(product);
			reviewDao.flush();
			staticService.build(product);
		}
		return pReview;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public Review update(Review review, String... ignoreProperties) {
		return super.update(review, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory", "review", "consultation" }, allEntries = true)
	public void delete(Review review) {
		if (review != null) {
			super.delete(review);
			Product product = review.getProduct();
			if (product != null) {
				reviewDao.flush();
				long totalScore = reviewDao.calculateTotalScore(product);
				long scoreCount = reviewDao.calculateScoreCount(product);
				product.setTotalScore(totalScore);
				product.setScoreCount(scoreCount);
				productDao.merge(product);
				reviewDao.flush();
				staticService.build(product);
			}
		}
	}

}