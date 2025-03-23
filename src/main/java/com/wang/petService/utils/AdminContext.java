package com.wang.petService.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wmx
 * @Date 2025/2/11 18:05
 */
public class AdminContext {
    private static final ThreadLocal<Map<String, Integer>> contextThreadLocal = ThreadLocal.withInitial(HashMap::new);

    public static void setAdminId(Integer id) {
        contextThreadLocal.get().put("adminId", id);
    }

    public static Integer getAdminId() {
        return contextThreadLocal.get().get("adminId");
    }

    public static void setUserId(Integer id) {
        contextThreadLocal.get().put("userId", id);
    }

    public static Integer getUserId() {
        return contextThreadLocal.get().get("userId");
    }

    public static void clear() {
        contextThreadLocal.remove();
    }
}