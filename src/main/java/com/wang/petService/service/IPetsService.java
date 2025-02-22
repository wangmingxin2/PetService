package com.wang.petService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Pet;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmx
 * @since 2025-02-03
 */
public interface IPetsService extends IService<Pet> {

    Page<Pet> selectLimit(Integer page, Integer pageSize, String name, String type);
}
