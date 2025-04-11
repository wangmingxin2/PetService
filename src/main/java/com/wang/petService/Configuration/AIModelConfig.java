package com.wang.petService.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
/**
 * @Author wmx
 * @Date 2025/4/11 9:23
 */

@Configuration
@ConfigurationProperties(prefix = "ai.model")
@Data
public class AIModelConfig {
    private String apiKey;
    private String baseUrl;
    private int timeout = 30000; // 默认超时时间（毫秒）
}