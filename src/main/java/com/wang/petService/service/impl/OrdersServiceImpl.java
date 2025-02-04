package com.wang.petService.service.impl;

import com.wang.petService.pojo.Order;
import com.wang.petService.mapper.OrdersMapper;
import com.wang.petService.service.IOrdersService;
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
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Order> implements IOrdersService {

}
