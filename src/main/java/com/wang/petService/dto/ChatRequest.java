package com.wang.petService.dto;

/**
 * @Author wmx
 * @Date 2025/4/11 9:27
 */

import java.util.List;
import lombok.Data;

@Data
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private Double temperature;
    private Integer max_tokens;

    @Data
    public static class Message {
        private String role; // 可以是 "system", "user", "assistant" 等
        private String content;

        public Message() {}

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
