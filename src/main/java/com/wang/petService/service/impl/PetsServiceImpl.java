package com.wang.petService.service.impl;

import com.wang.petService.pojo.Pet;
import com.wang.petService.mapper.PetsMapper;
import com.wang.petService.service.IPetsService;
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
public class PetsServiceImpl extends ServiceImpl<PetsMapper, Pet> implements IPetsService {

}
