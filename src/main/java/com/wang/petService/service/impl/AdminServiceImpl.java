package com.wang.petService.service.impl;

import com.wang.petService.pojo.Admin;
import com.wang.petService.mapper.AdminMapper;
import com.wang.petService.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmx
 * @since 2025-02-09
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin authenticate(String username, String password) {
        Admin admin = adminMapper.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
}
