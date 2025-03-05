package com.wang.petService.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.*;
import com.wang.petService.service.*;
import com.wang.petService.utils.Result;
import com.wang.petService.vo.OrderVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
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
    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private IServicesService iServicesService;
    @Autowired
    private IPetsService iPetsService;
    @Autowired
    private IServiceprovidersService iServiceprovidersService;

    @GetMapping
    public Result<Page<OrderVo>> getAllOrders(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(required = false) String userId,
                                              @RequestParam(required = false) String orderStatus) {
        // 获取订单分页数据
        Page<Order> pages = iOrdersService.selectLimit(page, pageSize, userId, orderStatus);

        // 如果没有订单记录，直接返回空分页
        if (pages.getRecords().isEmpty()) {
            Page<OrderVo> emptyPage = new Page<>(page, pageSize);
            emptyPage.setTotal(0);
            return Result.success(emptyPage);
        }

        // 使用提取的方法进行数据转换
        List<OrderVo> voList = convertToOrderVoList(pages.getRecords());

        // 创建并返回分页VO对象
        Page<OrderVo> voPage = new Page<>(page, pageSize);
        voPage.setTotal(pages.getTotal());
        voPage.setRecords(voList);

        return Result.success(voPage);
    }

    @GetMapping("/list")
    @ApiOperation(value = "Get all orders", notes = "Retrieve all orders transformed to OrderVo objects")
    public Result<List<OrderVo>> listAllOrders() {
        // 获取所有订单数据
        List<Order> orderList = iOrdersService.list();

        // 使用提取的方法进行数据转换
        List<OrderVo> voList = convertToOrderVoList(orderList);

        return Result.success(voList);
    }
    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Long id) {
        Order order = iOrdersService.getById(id);
        return Result.success(order);
    }

    @GetMapping("/totalAmount")
    @ApiOperation(value = "Get total order amount", notes = "Retrieve the sum of all order amounts")
    public Result<Integer> getTotalOrderAmount() {
        List<Order> orderList = iOrdersService.list();
        Integer sum = orderList.stream().collect(Collectors.summingInt(Order::getOrderAmount));
        return Result.success(sum);
    }
    @PostMapping
    public Result<String> createOrder(@RequestBody Order order) {
        boolean save = iOrdersService.save(order);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
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


    /**
     * 将Order列表转换为OrderVo列表
     * @param orderList 订单列表
     * @return OrderVo列表
     */
    private List<OrderVo> convertToOrderVoList(List<Order> orderList) {
        if (CollectionUtils.isEmpty(orderList)) {
            return new ArrayList<>();
        }

        // 收集所有关联ID，避免重复查询
        Set<Integer> userIds = new HashSet<>();
        Set<Integer> serviceIds = new HashSet<>();
        Set<Integer> petIds = new HashSet<>();
        Set<Integer> providerIds = new HashSet<>();

        for (Order order : orderList) {
            if (order.getUserId() != null) userIds.add(order.getUserId());
            if (order.getServiceId() != null) serviceIds.add(order.getServiceId());
            if (order.getPetId() != null) petIds.add(order.getPetId());
            if (order.getServiceProviderId() != null) providerIds.add(order.getServiceProviderId());
        }

        // 批量查询关联对象
        Map<Integer, User> userMap = CollectionUtils.isNotEmpty(userIds) ?
                iUsersService.listByIds(userIds).stream().collect(Collectors.toMap(User::getUserId, u -> u, (o1, o2) -> o1)) :
                new HashMap<>();

        Map<Integer, Service> serviceMap = CollectionUtils.isNotEmpty(serviceIds) ?
                iServicesService.listByIds(serviceIds).stream().collect(Collectors.toMap(Service::getServiceId, s -> s, (o1, o2) -> o1)) :
                new HashMap<>();

        Map<Integer, Pet> petMap = CollectionUtils.isNotEmpty(petIds) ?
                iPetsService.listByIds(petIds).stream().collect(Collectors.toMap(Pet::getPetId, p -> p, (o1, o2) -> o1)) :
                new HashMap<>();

        Map<Integer, Serviceprovider> providerMap = CollectionUtils.isNotEmpty(providerIds) ?
                iServiceprovidersService.listByIds(providerIds).stream().collect(Collectors.toMap(Serviceprovider::getServiceProviderId, p -> p, (o1, o2) -> o1)) :
                new HashMap<>();

        // 转换为VO对象
        return orderList.stream().map(order -> {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(order, orderVo);

            // 设置关联对象名称，规避空指针异常
            User user = userMap.get(order.getUserId());
            Service service = serviceMap.get(order.getServiceId());
            Pet pet = petMap.get(order.getPetId());
            Serviceprovider serviceprovider = providerMap.get(order.getServiceProviderId());

            orderVo.setUserName(user != null ? user.getName() : "未知用户");
            orderVo.setServiceName(service != null ? service.getName() : "未知服务");
            orderVo.setPetName(pet != null ? pet.getName() : "未知宠物");
            orderVo.setServiceProviderName(serviceprovider != null ? serviceprovider.getName() : "未知服务商");

            // 格式化订单金额
            if (order.getOrderAmount() != null) {
                orderVo.setOrderAmount(order.getOrderAmount());
            }

            return orderVo;
        }).collect(Collectors.toList());
    }
}
