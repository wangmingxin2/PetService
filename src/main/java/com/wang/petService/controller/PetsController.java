package com.wang.petService.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Pet;
import com.wang.petService.service.IPetsService;
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
@RequestMapping("/pet")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class PetsController {

    @Autowired
    private IPetsService iPetsService;

    @GetMapping
    public Result<Page<Pet>> list(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String type) {
        Page<Pet> pages = iPetsService.selectLimit(page, pageSize, name, type);
        return Result.success(pages);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get pet by ID", notes = "Retrieve a pet by its ID")
    public Result<Pet> getPetById(@PathVariable Long id) {
        Pet pet = iPetsService.getById(id);
        return Result.success(pet);
    }

    @PostMapping
    @ApiOperation(value = "Create a new pet", notes = "Create a new pet")
    public Result<String> createPet(@RequestBody Pet pet) {
        boolean save = iPetsService.save(pet);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update pet", notes = "Update an existing pet by its ID")
    public Result<String> updatePet(@RequestBody Pet pet) {
        boolean b = iPetsService.updateById(pet);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete pet", notes = "Delete an existing pet by its ID")
    public Result<String> deletePet(@PathVariable Long id) {
        boolean b = iPetsService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}
