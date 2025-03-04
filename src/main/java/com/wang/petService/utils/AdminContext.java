package com.wang.petService.utils;

/**
 * @Author wmx
 * @Date 2025/2/11 18:05
 */
public class AdminContext {
    private static final ThreadLocal<Integer> adminThreadLocal = new ThreadLocal<>();

    public static void setAdminId(Integer id) {
        adminThreadLocal.set(id);
    }

    public static Integer getAdminId() {
        return adminThreadLocal.get();
    }

    public static void clear() {
        adminThreadLocal.remove();
    }
}
