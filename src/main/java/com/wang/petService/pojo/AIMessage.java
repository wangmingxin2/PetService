package com.wang.petService.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author wmx
 * @Date 2025/4/11 15:36
 */
@AllArgsConstructor
@Data
public class AIMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String role;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    // 必需的无参构造函数
    public AIMessage() {
        this.timestamp = LocalDateTime.now();
    }
}