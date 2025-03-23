package com.wang.petService.Configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wang.petService.mapper.AdminMapper;
import com.wang.petService.mapper.UsersMapper;
import com.wang.petService.pojo.Admin;
import com.wang.petService.pojo.User;
import com.wang.petService.utils.AdminContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author wmx
 * @Date 2025/2/22 14:21
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Autowired
    @Lazy
    private AdminMapper adminMapper;
    @Autowired
    @Lazy
    private UsersMapper usersMapper;

    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("开始插入填充...");
        if (AdminContext.getAdminId() != null) {
            Admin admin = adminMapper.selectById(AdminContext.getAdminId());
            this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("updatedBy", admin.getUsername(), metaObject);
            this.setFieldValByName("createdBy", admin.getUsername(), metaObject);
        }else {
            User user = usersMapper.selectById(AdminContext.getUserId());
            this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("updatedBy", user.getName(), metaObject);
            this.setFieldValByName("createdBy", user.getName(), metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("开始更新填充...");
        if (AdminContext.getAdminId() != null) {
            Admin admin = adminMapper.selectById(AdminContext.getAdminId());
            this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("updatedBy", admin.getUsername(), metaObject);
        }else {
            User user = usersMapper.selectById(AdminContext.getUserId());
            this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("updatedBy", user.getName(), metaObject);
        }
    }
}