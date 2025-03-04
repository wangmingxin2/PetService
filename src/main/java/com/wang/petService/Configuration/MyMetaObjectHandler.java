package com.wang.petService.Configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wang.petService.mapper.AdminMapper;
import com.wang.petService.pojo.Admin;
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

    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("开始插入填充...");
        Admin admin = adminMapper.selectById(AdminContext.getAdminId());
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updatedBy", admin.getUsername(), metaObject);
        this.setFieldValByName("createdBy", admin.getUsername(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("开始更新填充...");
        Admin admin = adminMapper.selectById(AdminContext.getAdminId());
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updatedBy", admin.getUsername(), metaObject);
    }
}