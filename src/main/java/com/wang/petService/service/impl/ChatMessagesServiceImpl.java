package com.wang.petService.service.impl;

import com.wang.petService.pojo.ChatMessage;
import com.wang.petService.mapper.ChatMessagesMapper;
import com.wang.petService.pojo.User;
import com.wang.petService.service.IChatMessagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 聊天消息记录表 服务实现类
 * </p>
 *
 * @author wmx
 * @since 2025-03-10
 */
@Service
public class ChatMessagesServiceImpl extends ServiceImpl<ChatMessagesMapper, ChatMessage> implements IChatMessagesService {

    @Autowired
    private ChatMessagesMapper chatMessagesMapper;
    @Override
    public List<User> getUserConversations(Integer userId) {
        if (userId == null) {
            return new ArrayList<>();
        }
        // 调用Mapper查询与该用户有过聊天的所有用户信息
        return chatMessagesMapper.getUserConversationPartners(userId);
    }
}
