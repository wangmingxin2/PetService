package com.wang.petService.controller;

import com.wang.petService.dto.LoginRequest;
import com.wang.petService.pojo.Admin;
import com.wang.petService.service.IAdminService;
import com.wang.petService.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class AdminController {

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        Admin admin = iAdminService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (admin != null) {
            Map<String, Object> claims=new HashMap<>();
            claims.put("adminId",admin.getAdminId());
            String token = jwtUtil.getToken(claims);
            String redisTokenKey = "token:admin:" + admin.getAdminId();
            redisUtil.set(redisTokenKey, token, 1800);
            AdminContext.setAdminId(admin.getAdminId());
            return Result.success(token);
        } else {
            return Result.error(ResultEnum.USER_LOGIN_ERROR);
        }
    }
    @GetMapping("/profile")
    public Result<Admin> getAdminProfile() {
        Integer id = AdminContext.getAdminId();
        Admin admin = iAdminService.getById(id);
        return Result.success(admin);
    }
    @GetMapping
    public List<Admin> getAllAdmins() {
        return iAdminService.list();
    }

    @GetMapping("/{id}")
    public Result<Admin> getAdminById(@PathVariable Integer id) {
        Admin admin = iAdminService.getById(id);
        return Result.success(admin);
    }

    @PostMapping
    public Result<String> createAdmin(@RequestBody Admin admin) {
        boolean save = iAdminService.save(admin);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
    @PutMapping("/profile")
    public Result<String> updateAdminProfile(@RequestBody Admin updatedAdmin) {
        Integer id = AdminContext.getAdminId();
        Admin admin = iAdminService.getById(id);
        if (admin == null) {
            return Result.error(ResultEnum.USER_NOT_EXIST);
        }
        admin.setUsername(updatedAdmin.getUsername());
        admin.setEmail(updatedAdmin.getEmail());
        if (updatedAdmin.getPassword() != null) {
            admin.setPassword(updatedAdmin.getPassword());
        }
        boolean update = iAdminService.updateById(admin);
        if (update) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
    @PutMapping("/{id}")
    public Result<String> updateAdmin(@RequestBody Admin admin) {
        boolean b = iAdminService.updateById(admin);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteAdmin(@PathVariable Long id) {
        boolean b = iAdminService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}