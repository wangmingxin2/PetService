package com.wang.petService.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @Author wmx
 * @Date 2025/3/14 15:16
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxLoginResponse {
    private String token;
    private Integer userId;
    private String message;
}