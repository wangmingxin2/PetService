package com.wang.petService.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 聊天消息记录表
 * </p>
 *
 * @author wmx
 * @since 2025-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chat_messages")
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 接收者ID，为空表示广播消息
     */
    private String recipientId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private LocalDateTime sentTime;

    /**
     * 发送消息的客户端类型：WEB, wx
     */
    private String clientType;

    /**
     * 是否已读
     */
    private Boolean readStatus;


}
