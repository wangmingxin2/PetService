package com.wang.petService.interceptor;


import com.wang.petService.utils.AdminContext;
import com.wang.petService.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author wmx
 * @Date 2025/2/11 17:57
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        System.out.println("拦截器中的token: " + token);
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        Integer id = Integer.parseInt(jwtUtil.extractClaims(token).getSubject());
        System.out.println("拦截器中的adminId: " + id);
        if (jwtUtil.validateToken(token, id)) {
            AdminContext.setAdminId(id); // Store id in ThreadLocal
            System.out.println("请求中的adminId: " + AdminContext.getAdminId());
            return true; // token有效，放行请求
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("清理存储id的线程！");
        AdminContext.clear(); // Clear ThreadLocal to avoid memory leaks
    }
}