package com.wang.petService.vo;

import lombok.Data;

/**
 * @Author wmx
 * @Date 2025/3/14 15:16
 */
@Data
public class WxApiResponse {
    private String openid;
    private String session_key;
    private String unionid;
    private String errcode;
    private String errmsg;
}