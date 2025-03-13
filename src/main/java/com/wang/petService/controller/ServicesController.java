package com.wang.petService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Service;
import com.wang.petService.service.IServicesService;
import com.wang.petService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result<Page<Service>> getAllServices(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String status) {
        Page<Service> servicesPage = iServicesService.selectLimit(page, size, name, status);;
        return Result.success(servicesPage);
    }

    @GetMapping("/{id}")
    public Result<Service> getServiceById(@PathVariable Long id) {
        Service service = iServicesService.getById(id);
        return Result.success(service);
    }

    @PostMapping
    public Result<String> createService(@RequestBody Service service) {
        boolean save = iServicesService.save(service);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
    public Result<String> updateService(@RequestBody Service service) {
        boolean b = iServicesService.updateById(service);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteService(@PathVariable Long id) {
        boolean b = iServicesService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PatchMapping("/{id}/status")
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
