package com.wang.petService.interceptor;

import com.wang.petService.utils.AdminContext;
import com.wang.petService.utils.JwtUtil;
import com.wang.petService.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author wmx
 * @Date 2025/2/11 17:57
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
//        if (token == null || token.isEmpty()) {
//            return true;
//        }
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        // 提取声明
        Map<String, Object> claims = jwtUtil.extractClaims(token);
        Object openId = claims.get("openId");
        if (openId != null) {
            // 如果有openId，说明是小程序用户
            Integer userId = (Integer) claims.get("userId");
            AdminContext.setUserId(userId);
            return true;
        }
        // 获取admin映射并转换为Admin对象
        Integer adminId = (Integer) claims.get("adminId");

        String redisTokenKey = "token:admin:" + adminId;
        String RedisToken = (String) redisUtil.get(redisTokenKey);
        if (RedisToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        AdminContext.setAdminId(adminId);
        return true;
    }

    @Override
    public void afterCompletion(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AdminContext.clear();
    }
}