package com.wang.petService.controller;

import com.wang.petService.dto.LoginRequest;
import com.wang.petService.pojo.Admin;
import com.wang.petService.service.IAdminService;
import com.wang.petService.utils.AdminContext;
import com.wang.petService.utils.JwtUtil;
import com.wang.petService.utils.Result;
import com.wang.petService.utils.ResultEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
@Api(tags = "Admins", description = "Operations related to administrators")
public class AdminController {

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private JwtUtil jwtUtil;

    // Other CRUD methods...

    @PostMapping("/login")
    @ApiOperation(value = "Admin login", notes = "Authenticate admin and return JWT token")
    public Result<String> login(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        Admin admin = iAdminService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (admin != null) {
            String token = jwtUtil.getToken(admin.getAdminId());
            AdminContext.setAdminId(admin.getAdminId());
            System.out.println("设置的adminId: " + admin.getAdminId());
            return Result.success(token);
        } else {
            return Result.error(ResultEnum.USER_LOGIN_ERROR);
        }
    }
    @GetMapping("/profile")
    @ApiOperation(value = "Get admin profile", notes = "Retrieve the admin profile using the username from ThreadLocal")
    public Result<Admin> getAdminProfile() {
        Integer id = AdminContext.getAdminId();
        System.out.println("获取的adminId: " + id);
        Admin admin = iAdminService.getById(id);
        System.out.println(admin);
        return Result.success(admin);
//        if (username == null) {
//            return Result.error(ResultEnum.USER_NOT_EXIST);
//        }
//        Admin admin = iAdminService.getByUsername(username);
//        if (admin != null) {
//            return Result.success(admin);
//        } else {
//            return Result.error(ResultEnum.USER_NOT_EXIST);
//        }
    }
    @GetMapping
    public List<Admin> getAllAdmins() {
        return iAdminService.list();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get admin by ID", notes = "Retrieve an admin by its ID")
    public Result<Admin> getAdminById(@PathVariable Integer id) {
        Admin admin = iAdminService.getById(id);
        return Result.success(admin);
    }

    @PostMapping
    @ApiOperation(value = "Create a new admin", notes = "Create a new admin")
    public Result<String> createAdmin(@RequestBody Admin admin) {
        boolean save = iAdminService.save(admin);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
    @PutMapping("/profile")
    @ApiOperation(value = "Update admin profile", notes = "Update the admin profile with the provided username and email")
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
    @ApiOperation(value = "Update admin", notes = "Update an existing admin by its ID")
    public Result<String> updateAdmin(@RequestBody Admin admin) {
        boolean b = iAdminService.updateById(admin);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete admin", notes = "Delete an existing admin by its ID")
    public Result<String> deleteAdmin(@PathVariable Long id) {
        boolean b = iAdminService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}