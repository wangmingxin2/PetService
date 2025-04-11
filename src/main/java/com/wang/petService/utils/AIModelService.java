package com.wang.petService.utils;

/**
 * @Author wmx
 * @Date 2025/4/11 9:27
 */

import com.wang.petService.Configuration.AIModelConfig;
import com.wang.petService.dto.ChatRequest;
import com.wang.petService.vo.ChatResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AIModelService {

    private final AIModelConfig config;
    private final WebClient.Builder webClientBuilder;

    /**
     * 调用 AI 大模型进行聊天
     *
     * @param prompt 用户输入的提示
     * @return AI 的回复内容
     */
    public String generateResponse(String prompt) {
        WebClient client = webClientBuilder
                .baseUrl(config.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + config.getApiKey())
                .build();

        ChatRequest request = new ChatRequest();
        request.setModel("Qwen/Qwen2.5-VL-72B-Instruct"); // 根据实际模型名称调整
        request.setMessages(Arrays.asList(new ChatRequest.Message("user", prompt)));
        request.setTemperature(0.7);
        request.setMax_tokens(2000);

        try {
            ChatResponse response = client.post()
                    .uri("")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .timeout(Duration.ofMillis(config.getTimeout()))
                    .block();

            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            } else {
                log.error("AI 响应为空或格式不正确");
                return "抱歉，AI 响应存在问题，请稍后再试。";
            }
        } catch (Exception e) {
            log.error("调用 AI 模型时出错", e);
            return "调用 AI 模型服务失败: " + e.getMessage();
        }
    }
}
