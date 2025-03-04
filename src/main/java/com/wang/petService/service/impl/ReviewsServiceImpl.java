package com.wang.petService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Review;
import com.wang.petService.mapper.ReviewsMapper;
import com.wang.petService.pojo.Serviceprovider;
import com.wang.petService.service.IReviewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmx
 * @since 2025-02-03
 */
@Service
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Review> implements IReviewsService {

    @Autowired
    private ReviewsMapper reviewsMapper;
    @Override
    public Page<Review> selectLimit(Integer page, Integer pageSize, String orderId, String userId) {
        LambdaQueryWrapper<Review> queryWrapper = new LambdaQueryWrapper<>();

        if (orderId != null && !orderId.isEmpty()) {
            queryWrapper.like(Review::getOrderId, orderId);
        }

        if (userId != null && !userId.isEmpty()) {
            queryWrapper.like(Review::getUserId, userId);
        }

        Page<Review> Page = new Page<>(page, pageSize);
        return reviewsMapper.selectPage(Page, queryWrapper);
    }
}
