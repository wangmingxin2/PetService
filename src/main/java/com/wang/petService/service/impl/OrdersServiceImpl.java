package com.wang.petService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Order;
import com.wang.petService.mapper.OrdersMapper;
import com.wang.petService.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public Page<Order> selectLimit(Integer page, Integer pageSize, String userId, String status) {

        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

        if (userId != null && !userId.isEmpty()) {
            queryWrapper.eq(Order::getUserId, userId);
        }

        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(Order::getOrderStatus, status);
        }

        Page<Order> Page = new Page<>(page, pageSize);
        return ordersMapper.selectPage(Page, queryWrapper);
    }

    @Override
    public List<Order> listByStatus(String orderStatus) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        if (orderStatus != null && !orderStatus.isEmpty()) {
            queryWrapper.eq(Order::getOrderStatus, orderStatus);
        }
        return ordersMapper.selectList(queryWrapper);
    }

    @Override
    public boolean cancelOrderById(Integer orderId) {
        Order order = getById(orderId);
        if (order != null) {
            order.setOrderStatus("4"); //status  4 (已取消)
            return updateById(order);
        }
        return false;
    }
}
