package com.wang.petService.utils;

import com.google.gson.Gson;
import com.wang.petService.vo.WxApiResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WxApiService {

    @Value("${wechat.miniprogram.appid}")
    private String appId;

    @Value("${wechat.miniprogram.secret}")
    private String appSecret;

    private static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();

    private final Gson gson = new Gson();

    public WxApiResponse code2Session(String code) {
        String url = String.format(WX_LOGIN_URL, appId, appSecret, code);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("请求微信接口失败: " + response);
            }

            String responseBody = response.body().string();
            log.debug("微信登录接口响应: {}", responseBody);
            return gson.fromJson(responseBody, WxApiResponse.class);
        } catch (IOException e) {
            log.error("调用微信登录接口失败", e);
            WxApiResponse errorResponse = new WxApiResponse();
            errorResponse.setErrcode("-1");
            errorResponse.setErrmsg("微信登录失败");
            return errorResponse;
        }
    }
}