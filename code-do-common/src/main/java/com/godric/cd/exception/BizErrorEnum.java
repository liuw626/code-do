package com.godric.cd.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BizErrorEnum {

    NEED_LOGIN("NEED_LOGIN", "请先登录!"),
    NEED_ADMIN_PERMISSION("NEED_ADMIN_PERMISSION", "用户没有管理员权限!"),
    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在"),
    USERNAME_PASSWORD_NOT_MATCH("USERNAME_PASSWORD_NOT_MATCH", "用户名和密码不匹配"),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统开小差了, 请稍后再试!"),
    FIELD_CANNOT_NULL("FIELD_CANNOT_NULL", "字段 %s 不能为空"),
    PARAM_CANNOT_NULL("PARAM_CANNOT_NULL", "参数 %s 不能为空"),
    OPERATION_FAILED("OPERATION_FAILED", "操作失败"),
    ;

    private final String code;

    private final String desc;

}
