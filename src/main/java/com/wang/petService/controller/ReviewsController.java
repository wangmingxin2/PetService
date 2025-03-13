package com.wang.petService.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Review;
import com.wang.petService.service.IReviewsService;
import com.wang.petService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmx
 * @since 2025-02-03
 */
@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class ReviewsController {
    @Autowired
    private IReviewsService iReviewsService;

    @GetMapping
    public Result<Page<Review>> getAllReviews(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(required = false) String orderId,
                                      @RequestParam(required = false) String userId) {
        Page<Review> pages = iReviewsService.selectLimit(page, pageSize, orderId, userId);
        return Result.success(pages);
    }

    @GetMapping("/{id}")
    public Result<Review> getReviewById(@PathVariable Long id) {
        Review review = iReviewsService.getById(id);
        return Result.success(review);
    }

    @PostMapping
    public Result<String> createReview(@RequestBody Review review) {
        boolean save = iReviewsService.save(review);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
    public Result<String> updateReview(@RequestBody Review review) {
        boolean b = iReviewsService.updateById(review);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteReview(@PathVariable Long id) {
        boolean b = iReviewsService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}
