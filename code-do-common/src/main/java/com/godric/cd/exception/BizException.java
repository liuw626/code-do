package com.godric.cd.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private String code;

    private String message;

    public BizException(BizErrorEnum bizErrorEnum) {
        this.code = bizErrorEnum.getCode();
        this.message = bizErrorEnum.getDesc();
    }

    public BizException(BizErrorEnum bizErrorEnum, Object... args) {
        this.code = bizErrorEnum.getCode();
        this.message = this.formatMessage(bizErrorEnum.getDesc(), args);
    }

    public BizException(String message) {
        this.message = message;
    }

    private String formatMessage(String message, Object... args) {
        return String.format(message, args);
    }


}
