package com.godric.cd.result;

import com.godric.cd.exception.BizErrorEnum;

import java.io.Serializable;

public class BaseResult implements Serializable {

    private boolean success;

    private String errorCode;

    private String message;

    public static BaseResult success() {
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        return result;
    }

    public static BaseResult fail(String message) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        result.setMessage(message);

        return result;
    }

    public static BaseResult fail(BizErrorEnum bizErrorEnum) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        result.setErrorCode(bizErrorEnum.getCode());
        result.setMessage(bizErrorEnum.getDesc());

        return result;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
