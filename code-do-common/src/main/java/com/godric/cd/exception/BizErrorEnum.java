package com.godric.cd.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BizErrorEnum {

    SYSTEM_ERROR("SYSTEM_ERROR", "系统开小差了, 请稍后再试!"),
    NEED_LOGIN("NEED_LOGIN", "请先登录!"),
    PARAM_CANNOT_NULL("PARAM_CANNOT_NULL", "参数 %s 不能为空"),
    OPERATION_FAILED("OPERATION_FAILED", "操作失败"),
    NOT_MP_MESSAGE("NOT_MP_MESSAGE", "不是来自公众号的消息"),
    INVALID_MP_REQUEST("INVALID_MP_REQUEST", "非法的公众号请求"),
    UNKNOWN_ENCRYPT_TYPE("UNKNOWN_ENCRYPT_TYPE", "未知加密类型"),
    INVALID_ROUTER("INVALID_ROUTER", "消息路由失败"),
    WECHAT_SERVICE_ERROR("WECHAT_SERVICE_ERROR", "调用微信接口失败"),
    ;

    private final String code;

    private final String desc;

}
