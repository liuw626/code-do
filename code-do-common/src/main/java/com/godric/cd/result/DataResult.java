package com.godric.cd.result;

import com.godric.cd.exception.BizErrorEnum;

public class DataResult<T> extends BaseResult {

    private T data;

    public static <T> DataResult<T> success(T data) {
        DataResult<T> result = new DataResult<T>();
        result.setSuccess(true);
        result.setData(data);

        return result;
    }

    public static <T> DataResult<T> error() {
        DataResult<T> result = new DataResult<T>();
        result.setSuccess(false);
        return result;
    }

    public static <T> DataResult<T> error(String message) {
        DataResult<T> result = new DataResult<T>();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

    public static <T> DataResult<T> error(BizErrorEnum bizErrorEnum) {
        DataResult<T> result = new DataResult<T>();
        result.setSuccess(false);
        result.setErrorCode(bizErrorEnum.getCode());
        result.setMessage(bizErrorEnum.getDesc());
        return result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
