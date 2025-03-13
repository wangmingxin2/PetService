package com.wang.petService.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;
/**
 * @Author wmx
 * @Date 2025/3/7 11:12
 */

@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存储数据到Redis
     * @param key 键
     * @param value 值
     * @param timeout 过期时间(秒)，如果小于等于0则永不过期
     * @return 操作是否成功
     */
    public boolean set(String key, Object value, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从Redis获取数据
     * @param key 键
     * @return 值，如果key不存在则返回null
     */
//    public <T> T get(String key) {
//        if(key==null){
//            return null;
//        }
//        try {
//            return (T)redisTemplate.opsForValue().get(key);
//        } catch (Exception e) {
//            log.error("RedisUtil get error: e=" + e);
//            log.error("RedisUtil get error: key=" + key);
//            return null;
//        }
//    }
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}