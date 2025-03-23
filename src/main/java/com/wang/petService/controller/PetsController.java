package com.wang.petService.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.Pet;
import com.wang.petService.service.IPetsService;
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

    @GetMapping("/list")
    public Result<List<Pet>> getAllPets() {
        List<Pet> pets = iPetsService.list();
        return Result.success(pets);
    }
    @GetMapping("/listByUserId/{userId}")
    public Result<List<Pet>> getAllPetByUserId(@PathVariable Long userId) {
        List<Pet> pets = iPetsService.listByUserId(userId);
        return Result.success(pets);
    }
    @GetMapping("/{id}")
    public Result<Pet> getPetById(@PathVariable Long id) {
        Pet pet = iPetsService.getById(id);
        return Result.success(pet);
    }

    @PostMapping
    public Result<String> createPet(@RequestBody Pet pet) {
        boolean save = iPetsService.save(pet);
        if (save) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/{id}")
    public Result<String> updatePet(@RequestBody Pet pet) {
        boolean b = iPetsService.updateById(pet);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    public Result<String> deletePet(@PathVariable Long id) {
        boolean b = iPetsService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}
