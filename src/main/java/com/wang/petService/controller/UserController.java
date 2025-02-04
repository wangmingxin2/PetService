package com.wang.petService.controller;


import com.wang.petService.pojo.User;
import com.wang.petService.service.IUsersService;
import com.wang.petService.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@Api(tags = "User", description = "Operations related to users")
public class UserController {

    @Autowired
    private IUsersService iUsersService;

    @GetMapping
    public List<User> getAllUsers() {
        return iUsersService.list();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get user by ID", notes = "Retrieve a user by their ID")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = iUsersService.getById(id);
        return Result.success(user);
    }

    @PostMapping
    @ApiOperation(value = "Create a new user", notes = "Create a new user")
    public Result<String> createUser(@RequestBody User user) {
        boolean save = iUsersService.save(user);
        if (save) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update user", notes = "Update an existing user by their ID")
    public Result<String> updateUser(@RequestBody User user) {
        boolean b = iUsersService.updateById(user);
        if (b) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user", notes = "Delete an existing user by their ID")
    public Result<String> deleteUser(@PathVariable Long id) {
        boolean b = iUsersService.removeById(id);
        if (b) {
            return Result.success();
        }else {
            return Result.error();
        }
    }
}