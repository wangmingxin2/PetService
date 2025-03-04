package com.wang.petService.mapper;

import com.wang.petService.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wmx
 * @since 2025-02-09
 */
public interface AdminMapper extends BaseMapper<Admin> {

    Admin findByUsername(String username);
}
