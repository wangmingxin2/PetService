package com.wang.petService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Pet;
import com.wang.petService.mapper.PetsMapper;
import com.wang.petService.service.IPetsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PetsServiceImpl extends ServiceImpl<PetsMapper, Pet> implements IPetsService {
    @Autowired
    private PetsMapper petsMapper;

    @Override
    public Page<Pet> selectLimit(Integer page, Integer pageSize, String name, String type) {
        LambdaQueryWrapper<Pet> queryWrapper = new LambdaQueryWrapper<>();

        if (name != null && !name.isEmpty()) {
            queryWrapper.like(Pet::getName, name);
        }

        if (type != null && !type.isEmpty()) {
            queryWrapper.eq(Pet::getType, type);
        }

        IPage<Pet> Page = new Page<>(page, pageSize);
        return (Page<Pet>) petsMapper.selectPage(Page, queryWrapper);
    }
}
