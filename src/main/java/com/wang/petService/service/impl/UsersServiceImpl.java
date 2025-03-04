package com.wang.petService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Pet;
import com.wang.petService.pojo.User;
import com.wang.petService.mapper.UsersMapper;
import com.wang.petService.service.IUsersService;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, User> implements IUsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Page<User> selectLimit(int page, int size, String name, String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        if (name != null && !name.isEmpty()) {
            queryWrapper.like(User::getName, name);
        }

        if (phone != null && !phone.isEmpty()) {
            queryWrapper.eq(User::getPhone, phone);
        }

        Page<User> Page = new Page<>(page, size);
        return usersMapper.selectPage(Page, queryWrapper);
    }
}
