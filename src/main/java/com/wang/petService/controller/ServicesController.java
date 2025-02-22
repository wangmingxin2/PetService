package com.wang.petService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Service;
import com.wang.petService.service.IServicesService;
import com.wang.petService.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wmx
 * @since 2025-02-03
 */
@RestController
@RequestMapping("/service")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class ServicesController {
    @Autowired
    private IServicesService iServicesService;

    @GetMapping
    @ApiOperation(value = "Get all services with pagination", notes = "Retrieve all services with pagination support")
    public Result<Page<Service>> getAllServices(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Page<Service> servicePage = new Page<>(page, size);
        Page<Service> servicesPage = iServicesService.page(servicePage);
        return Result.success(servicesPage);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get service by ID", notes = "Retrieve a service by its ID")
    public Result<Service> getServiceById(@PathVariable Long id) {
        Service service = iServicesService.getById(id);
        return Result.success(service);
    }

    @PostMapping
    @ApiOperation(value = "Create a new service", notes = "Create a new service")
    public Result<String> createService(@RequestBody Service service) {
        boolean save = iServicesService.save(service);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update service", notes = "Update an existing service by its ID")
    public Result<String> updateService(@RequestBody Service service) {
        boolean b = iServicesService.updateById(service);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete service", notes = "Delete an existing service by its ID")
    public Result<String> deleteService(@PathVariable Long id) {
        boolean b = iServicesService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PatchMapping("/{id}/status")
    @ApiOperation(value = "Update service status", notes = "Update the status of an existing service by its ID")
    public Result<String> updateServiceStatus(@PathVariable Long id, @RequestParam String status) {
        Service service = iServicesService.getById(id);
        if (service == null) {
            return Result.error("Service not found");
        }
        service.setStatus(status);
        boolean update = iServicesService.updateById(service);
        if (update) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
    @PostMapping("/search")
    @ApiOperation(value = "Search services by name and status", notes = "Retrieve services based on their name and status")
    public Result<List<Service>> searchServices(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String status) {
        QueryWrapper<Service> queryWrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like("name", name);
        }
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq("status", status);
        }
        List<Service> services = iServicesService.list(queryWrapper);
        return Result.success(services);
    }
}
