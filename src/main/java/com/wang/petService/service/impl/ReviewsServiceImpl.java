package com.wang.petService.service.impl;

import com.wang.petService.pojo.Review;
import com.wang.petService.mapper.ReviewsMapper;
import com.wang.petService.service.IReviewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
