package com.wang.petService.utils;
import lombok.Getter;
/**
 * @Author wmx
 * @Date 2025/2/4 19:38
 */

@Getter
public enum ResultEnum {

    /* 成功状态码 */
    SUCCESS(200, "操作成功！"),

    /* 错误状态码 */
    FAIL(400, "操作失败！"),

    /* 参数错误：400-499 */
    PARAM_IS_INVALID(401, "参数无效"),
    PARAM_IS_BLANK(402, "参数为空"),
    PARAM_TYPE_BIND_ERROR(403, "参数格式错误"),
    PARAM_NOT_COMPLETE(404, "参数缺失"),

    /* 用户错误：401-499 */
    USER_NOT_LOGGED_IN(401, "用户未登录，请先登录"),
    USER_LOGIN_ERROR(402, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(403, "账号已被禁用"),
    USER_NOT_EXIST(401, "用户不存在"),
    USER_HAS_EXISTED(405, "用户已存在"),

    /* 系统错误：500 */
    FILE_MAX_SIZE_OVERFLOW(500, "上传尺寸过大"),
    FILE_ACCEPT_NOT_SUPPORT(500, "上传文件格式不支持"),

    /* 数据错误：500 */
    RESULT_DATA_NONE(500, "数据未找到"),
    DATA_IS_WRONG(500, "数据有误"),
    DATA_ALREADY_EXISTED(500, "数据已存在"),
    AUTH_CODE_ERROR(500, "验证码错误"),

    /* 权限错误：401-499 */
    PERMISSION_UNAUTHENTICATED(401, "此操作需要登陆系统！"),
    PERMISSION_UNAUTHORIZED(403, "权限不足，无权操作！"),
    PERMISSION_EXPIRE(401, "登录状态过期！"),
    PERMISSION_TOKEN_EXPIRED(401, "token已过期"),
    PERMISSION_LIMIT(429, "访问次数受限制"),
    PERMISSION_TOKEN_INVALID(401, "无效token"),
    PERMISSION_SIGNATURE_ERROR(401, "签名失败");
    // 状态码
    int code;
    // 提示信息
    String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}