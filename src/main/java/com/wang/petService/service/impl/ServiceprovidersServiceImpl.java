package com.wang.petService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Serviceprovider;
import com.wang.petService.mapper.ServiceprovidersMapper;
import com.wang.petService.service.IServiceprovidersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wmx
 * @since 2025-02-03
 */
@Service
public class ServiceprovidersServiceImpl extends ServiceImpl<ServiceprovidersMapper, Serviceprovider> implements IServiceprovidersService {

    @Autowired
    private ServiceprovidersMapper serviceprovidersMapper;
    @Override
    public Page<Serviceprovider> selectLimit(int page, int size, String name, String phone) {
        LambdaQueryWrapper<Serviceprovider> queryWrapper = new LambdaQueryWrapper<>();

        if (name != null && !name.isEmpty()) {
            queryWrapper.like(Serviceprovider::getName, name);
        }

        if (phone != null && !phone.isEmpty()) {
            queryWrapper.like(Serviceprovider::getPhone, phone);
        }

        Page<Serviceprovider> Page = new Page<>(page, size);
        return serviceprovidersMapper.selectPage(Page, queryWrapper);
    }
}
