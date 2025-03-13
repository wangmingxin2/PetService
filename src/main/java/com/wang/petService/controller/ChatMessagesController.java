package com.wang.petService.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.petService.pojo.ChatMessage;
import com.wang.petService.pojo.User;
import com.wang.petService.service.IChatMessagesService;
import com.wang.petService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 聊天消息记录表 前端控制器
 * </p>
 *
 * @author wmx
 * @since 2025-03-10
 */
@RestController
@RequestMapping("/chat")
public class ChatMessagesController {

    @Autowired
    private IChatMessagesService chatMessagesService;
    /**
     * 查询双方聊天记录
     *
     * @param userId1 用户1的ID
     * @param userId2 用户2的ID
     * @param page    页码
     * @param size    每页大小
     * @return 聊天记录列表
     */
    @GetMapping
    public Result<Page<ChatMessage>> getConversation(
            @RequestParam String userId1,
            @RequestParam String userId2,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size)
    {
        // 创建分页对象
        Page<ChatMessage> pageParam = new Page<>(page, size);
        // 构建查询条件：查询两个用户之间的聊天记录
        LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();

        // 两个用户之间的聊天记录包括：userId1发给userId2的 或 userId2发给userId1的
        queryWrapper.and(wrapper -> wrapper
                .and(w -> w.eq(ChatMessage::getSenderId, userId1)
                        .eq(ChatMessage::getRecipientId, userId2))
                .or(w -> w.eq(ChatMessage::getSenderId, userId2)
                        .eq(ChatMessage::getRecipientId, userId1))
        );

        // 按发送时间升序排序
        queryWrapper.orderByDesc(ChatMessage::getSentTime);

        // 执行分页查询
        Page<ChatMessage> result = chatMessagesService.page(pageParam, queryWrapper);

        return Result.success(result);

    }

    /**
     * 查询指定用户的所有聊天会话列表
     *
     * @param userId 用户ID
     * @return 会话列表
     */
    @GetMapping("/{userId}")
    public Result<List<User>> getUserConversations(@PathVariable Integer userId) {
        List<User> conversations = chatMessagesService.getUserConversations(userId);
        return Result.success(conversations);
    }


}