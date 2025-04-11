package com.wang.petService.vo;

/**
 * @Author wmx
 * @Date 2025/4/11 9:27
 */
import java.util.List;

import com.wang.petService.dto.ChatRequest;
import lombok.Data;

@Data
public class ChatResponse {
    private List<Choice> choices;
    private String id;
    private String object;
    private Long created;
    private String model;
    private Usage usage;

    @Data
    public static class Choice {
        private Integer index;
        private ChatRequest.Message message;
        private String finish_reason;
    }

    @Data
    public static class Usage {
        private Integer prompt_tokens;
        private Integer completion_tokens;
        private Integer total_tokens;
    }
}