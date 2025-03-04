package com.wang.petService.controller;


import com.wang.petService.service.FileUploadService;
import com.wang.petService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
/**
 * @Author wmx
 * @Date 2025/3/3 21:28
 */
@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "*")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileUploadService.uploadFile(file);
            System.out.println(fileName);
            return Result.success(fileName);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}