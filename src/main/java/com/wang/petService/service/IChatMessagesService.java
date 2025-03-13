package com.wang.petService.service;

import com.wang.petService.pojo.ChatMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.petService.pojo.User;

import java.util.List;

/**
 * <p>
 * 聊天消息记录表 服务类
 * </p>
 *
 * @author wmx
 * @since 2025-03-10
 */
public interface IChatMessagesService extends IService<ChatMessage> {

    List<User> getUserConversations(Integer userId);
}
