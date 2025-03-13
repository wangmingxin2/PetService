package com.wang.petService.utils;

import com.wang.petService.pojo.ChatMessage;
import com.wang.petService.service.IChatMessagesService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{userId}")
@Component
@Slf4j
public class WebSocketServer {
    // 静态变量，所有实例共享
    private static IChatMessagesService iChatMessagesService;

    // 通过setter方法注入，这个方法会在应用启动时被Spring调用一次
    @Autowired
    public void setIChatMessagesService(IChatMessagesService service) {
        log.info("IChatMessagesService注入成功: {}", (service != null));
        WebSocketServer.iChatMessagesService = service;
    }
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    // 使用ConcurrentHashMap存储用户ID和对应的WebSocket会话
    private static final Map<String, Session> userSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        logger.info("用户 {} 已连接到WebSocket服务器，会话ID: {}", userId, session.getId());
        // 将用户ID和会话关联起来
        userSessions.put(userId, session);
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("userId") String userId) {
        logger.info("收到来自用户 {} 的消息: {}", userId, message);

        try {
            // 解析消息，假设格式为JSON: {to: "接收者ID", content: "消息内容"}
            Map<String, String> msgMap = parseMessage(message);
            String toUser = msgMap.get("toUserId");
            String content = msgMap.get("content");

            // 发送给指定用户
            if (toUser != null && content != null) {
                sendToUser(toUser, createMessage(userId, content));
                ChatMessage chatMessage = new ChatMessage().setSenderId(userId).setRecipientId(toUser).setClientType("1").setContent(content).setSentTime(LocalDateTime.now());
                iChatMessagesService.save(chatMessage);
            } else {// 无效消息格式
                sendMessage(session, createMessage("system", "无效的消息格式，请使用 {to: '用户ID', content: '消息内容'}"));
            }

        } catch (Exception e) {
            logger.error("处理消息时出错: {}", e.getMessage(), e);
            sendMessage(session, createMessage("system", "消息处理失败: " + e.getMessage()));
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        logger.info("用户 {} 已断开WebSocket连接", userId);
        userSessions.remove(userId);

        // 广播用户下线通知
        broadcastMessage(createMessage("system", "用户 " + userId + " 下线了"), null);
    }

    @OnError
    public void onError(Session session, Throwable error, @PathParam("userId") String userId) {
        logger.error("WebSocket错误，来自用户 {}: {}", userId, error.getMessage(), error);
        userSessions.remove(userId);
    }

    /**
     * 向特定用户发送消息
     */
    public void sendToUser(String userId, String message) {
        Session session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            sendMessage(session, message);
            // 发送确认消息给发送者
            logger.info("消息已发送给用户 {}", userId);
        } else {
            logger.warn("用户 {} 不在线或会话已关闭", userId);
        }
    }

    /**
     * 向指定的WebSocket会话发送消息
     */
    private void sendMessage(Session session, String message) {
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            logger.error("发送消息失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 广播消息给所有用户，可以排除特定用户
     */
    public void broadcastMessage(String message, String excludeUserId) {
        userSessions.forEach((userId, session) -> {
            if ((excludeUserId == null || !userId.equals(excludeUserId)) && session.isOpen()) {
                sendMessage(session, message);
            }
        });
    }

    /**
     * 创建格式化的消息
     */
    private String createMessage(String from, String content) {
        return String.format("{\"from\":\"%s\",\"content\":\"%s\",\"timestamp\":%d}",
                from, content, System.currentTimeMillis());
    }

    /**
     * 解析收到的消息
     */
    private Map<String, String> parseMessage(String message) {
        // 简单实现，实际项目中可以使用JSON库如Jackson或Gson
        Map<String, String> result = new ConcurrentHashMap<>();

        // 去除花括号和空格
        message = message.trim();
        if (message.startsWith("{")) {
            message = message.substring(1);
        }
        if (message.endsWith("}")) {
            message = message.substring(0, message.length() - 1);
        }

        // 解析键值对
        String[] pairs = message.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                if (key.startsWith("\"") && key.endsWith("\"")) {
                    key = key.substring(1, key.length() - 1);
                }

                String value = keyValue[1].trim();
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }

                result.put(key, value);
            }
        }

        return result;
    }

    /**
     * 获取当前连接数
     */
    public static int getConnectionCount() {
        return userSessions.size();
    }

    /**
     * 获取所有在线用户ID
     */
    public static String[] getOnlineUsers() {
        return userSessions.keySet().toArray(new String[0]);
    }
}