package com.wang.petService.service;

import com.wang.petService.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmx
 * @since 2025-02-09
 */
public interface IAdminService extends IService<Admin> {

    Admin authenticate(String username, String password);

}
