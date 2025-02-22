package com.wang.petService.controller;


import com.wang.petService.pojo.Serviceprovider;
import com.wang.petService.service.IServiceprovidersService;
import com.wang.petService.utils.Result;
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
@RequestMapping("/serviceprovider")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class ServiceprovidersController {
    @Autowired
    private IServiceprovidersService iServiceprovidersService;

    @GetMapping
    public List<Serviceprovider> getAllServiceproviders() {
        return iServiceprovidersService.list();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get service provider by ID", notes = "Retrieve a service provider by its ID")
    public Result<Serviceprovider> getServiceproviderById(@PathVariable Long id) {
        Serviceprovider serviceprovider = iServiceprovidersService.getById(id);
        return Result.success(serviceprovider);
    }

    @PostMapping
    @ApiOperation(value = "Create a new service provider", notes = "Create a new service provider")
    public Result<String> createServiceprovider(@RequestBody Serviceprovider serviceprovider) {
        boolean save = iServiceprovidersService.save(serviceprovider);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update service provider", notes = "Update an existing service provider by its ID")
    public Result<String> updateServiceprovider(@RequestBody Serviceprovider serviceprovider) {
        boolean b = iServiceprovidersService.updateById(serviceprovider);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete service provider", notes = "Delete an existing service provider by its ID")
    public Result<String> deleteServiceprovider(@PathVariable Long id) {
        boolean b = iServiceprovidersService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}
