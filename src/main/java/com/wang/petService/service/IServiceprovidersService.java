package com.wang.petService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Serviceprovider;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmx
 * @since 2025-02-03
 */
public interface IServiceprovidersService extends IService<Serviceprovider> {

    Page<Serviceprovider> selectLimit(int page, int size, String name, String phone);
}
