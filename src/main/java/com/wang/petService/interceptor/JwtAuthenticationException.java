package com.wang.petService.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * @Author wmx
 * @Date 2025/3/11 14:53
 */

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}