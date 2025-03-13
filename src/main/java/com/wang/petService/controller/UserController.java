package com.wang.petService.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.User;
import com.wang.petService.service.IUsersService;
import com.wang.petService.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wmx
 * @since 2025-02-03
 */


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class UserController {

    @Autowired
    private IUsersService iUsersService;

    @GetMapping
    public Result<Page<User>> getUsersWithPagination(@RequestParam(defaultValue = "1") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String phone) {
        Page<User> paginatedUsers = iUsersService.selectLimit(page, size, name, phone);;
        return Result.success(paginatedUsers);
    }
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = iUsersService.getById(id);
        return Result.success(user);
    }

    @PostMapping
    public Result<String> createUser(@RequestBody User user) {
        boolean save = iUsersService.save(user);
        if (save) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
    public Result<String> updateUser(@RequestBody User user) {
        boolean b = iUsersService.updateById(user);
        if (b) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        boolean b = iUsersService.removeById(id);
        if (b) {
            return Result.success();
        }else {
            return Result.error();
        }
    }
}