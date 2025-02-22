package com.wang.petService.controller;


import com.wang.petService.pojo.Order;
import com.wang.petService.service.IOrdersService;
import com.wang.petService.utils.Result;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/order")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class OrdersController {

    @Autowired
    private IOrdersService iOrdersService;

    @GetMapping
    public List<Order> getAllOrders() {
        return iOrdersService.list();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get order by ID", notes = "Retrieve an order by its ID")
    public Result<Order> getOrderById(@PathVariable Long id) {
        Order order = iOrdersService.getById(id);
        return Result.success(order);
    }

    @PostMapping
    @ApiOperation(value = "Create a new order", notes = "Create a new order")
    public Result<String> createOrder(@RequestBody Order order) {
        boolean save = iOrdersService.save(order);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update order", notes = "Update an existing order by its ID")
    public Result<String> updateOrder(@RequestBody Order order) {
        boolean b = iOrdersService.updateById(order);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete order", notes = "Delete an existing order by its ID")
    public Result<String> deleteOrder(@PathVariable Long id) {
        boolean b = iOrdersService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}
