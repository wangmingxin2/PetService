package com.wang.petService.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Serviceprovider;
import com.wang.petService.service.IServiceprovidersService;
import com.wang.petService.utils.Result;
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
@RequestMapping("/serviceprovider")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class ServiceprovidersController {
    @Autowired
    private IServiceprovidersService iServiceprovidersService;

    @GetMapping
    public Result<Page<Serviceprovider>> getAllServiceproviders(@RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(required = false) String name,
                                                                @RequestParam(required = false) String phone) {
        Page<Serviceprovider> serviceproviderPage = iServiceprovidersService.selectLimit(page, size, name, phone);;
        return Result.success(serviceproviderPage);
    }

    @GetMapping("/list")
    public Result<List<Serviceprovider>> listServiceprovider() {
        List<Serviceprovider> serviceprovider = iServiceprovidersService.list();
        return Result.success(serviceprovider);
    }
    @GetMapping("/{id}")
    public Result<Serviceprovider> getServiceproviderById(@PathVariable Long id) {
        Serviceprovider serviceprovider = iServiceprovidersService.getById(id);
        return Result.success(serviceprovider);
    }

    @PostMapping
    public Result<String> createServiceprovider(@RequestBody Serviceprovider serviceprovider) {
        boolean save = iServiceprovidersService.save(serviceprovider);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
    public Result<String> updateServiceprovider(@RequestBody Serviceprovider serviceprovider) {
        boolean b = iServiceprovidersService.updateById(serviceprovider);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteServiceprovider(@PathVariable Long id) {
        boolean b = iServiceprovidersService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}
