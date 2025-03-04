package com.wang.petService.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wang.petService.pojo.Messages;
import com.wang.petService.service.IMessagesService;
import com.wang.petService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private IMessagesService messageService;

    @GetMapping("/unread/count")
    public Result<Integer> getUnreadCount() {
        int count = messageService.getUnreadCount();
        return Result.success(count);
    }

    @GetMapping("/list")
    public Result<List<Messages>> getMessageList() {
        List<Messages> messages = messageService.list();
        return Result.success(messages);
    }

    @PutMapping("/{id}/read")
    public Result<String> markMessageAsRead(@PathVariable Long id) {
        UpdateWrapper<Messages> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("is_read", true);
        boolean update = messageService.update(updateWrapper);
        if (update) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/read/all")
    public Result<String> markAllMessagesAsRead() {
        boolean update = messageService.markAllAsRead();
        if (update) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}