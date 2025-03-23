package com.wang.petService.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wang.petService.mapper.UsersMapper;
import com.wang.petService.pojo.User;
import com.wang.petService.vo.WxApiResponse;
import com.wang.petService.vo.WxLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wmx
 * @Date 2025/3/14 15:37
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WxLoginService {

    private final WxApiService wxApiService;
    private final UsersMapper usersMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public WxLoginResponse login(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("登录失败：code不能为空");
        }

        // 调用微信API，用code换取openId
        WxApiResponse wxApiResponse = wxApiService.code2Session(code);

        // 检查微信返回结果
        if ("-1".equals(wxApiResponse.getErrcode())) {
            log.error("微信登录失败: {}", wxApiResponse.getErrmsg());
            throw new RuntimeException("微信登录失败: " + wxApiResponse.getErrmsg());
        }

        String openId = wxApiResponse.getOpenid();
        log.info("获取到用户openId: {}", openId);

        // 查找或创建用户
        User user = findOrCreateUser(openId, wxApiResponse.getOpenid());
        Map<String, Object> claims =new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("openId", user.getOpenId());
        // 生成JWT令牌
        String token = jwtUtil.getToken(claims);

        // 返回登录结果
        return new WxLoginResponse(token, user.getUserId(), "登录成功");
    }

    private User findOrCreateUser(String openId, String unionId) {
        // 使用MyBatis-Plus查询用户
        User existingUser = usersMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getOpenId, openId)
        );

        if (existingUser != null) {
            return existingUser;
        } else {
            // 创建新用户
            User newUser = new User();
            newUser.setOpenId(openId);
            usersMapper.insert(newUser);
            return newUser;
        }
    }
}