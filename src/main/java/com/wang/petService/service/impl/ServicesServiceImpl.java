package com.wang.petService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Service;
import com.wang.petService.mapper.ServicesMapper;
import com.wang.petService.pojo.User;
import com.wang.petService.service.IServicesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wmx
 * @since 2025-02-03
 */
@org.springframework.stereotype.Service
public class ServicesServiceImpl extends ServiceImpl<ServicesMapper, Service> implements IServicesService {

    @Autowired
    private ServicesMapper servicesMapper;
    @Override
    public Page<Service> selectLimit(int page, int size, String name, String status) {
        LambdaQueryWrapper<Service> queryWrapper = new LambdaQueryWrapper<>();

        if (name != null && !name.isEmpty()) {
            queryWrapper.like(Service::getName, name);
        }

        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(Service::getStatus, status);
        }

        Page<Service> Page = new Page<>(page, size);
        return servicesMapper.selectPage(Page, queryWrapper);
    }
}
