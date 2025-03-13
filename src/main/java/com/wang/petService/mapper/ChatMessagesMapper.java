package com.wang.petService.mapper;

import com.wang.petService.pojo.ChatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.petService.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 聊天消息记录表 Mapper 接口
 * </p>
 *
 * @author wmx
 * @since 2025-03-10
 */
public interface ChatMessagesMapper extends BaseMapper<ChatMessage> {
    @Select({
            "SELECT DISTINCT u.* FROM users u ",
            "WHERE u.user_id IN ( ",
            "    SELECT DISTINCT ",
            "        CASE ",
            "            WHEN cm.sender_id = #{userId} THEN cm.recipient_id ",
            "            WHEN cm.recipient_id = #{userId} THEN cm.sender_id ",
            "        END as partner_id ",
            "    FROM chat_messages cm ",
            "    WHERE cm.sender_id = #{userId} OR cm.recipient_id = #{userId} ",
            ")"
    })
    List<User> getUserConversationPartners(Integer userId);
}
