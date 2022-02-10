package com.godric.cd.result;

import com.godric.cd.exception.BizErrorEnum;

import java.util.List;

public class ListResult<T> extends BaseResult {

    private List<T> data;

    private int totalCount;

    public static <T> ListResult<T> success(List<T> data) {
        ListResult<T> result = new ListResult<T>();
        result.setSuccess(true);
        result.setData(data);
        result.setTotalCount(data.size());

        return result;
    }

    public static <T> ListResult<T> error() {
        ListResult<T> result = new ListResult<T>();
        result.setSuccess(false);
        return result;
    }

    public static <T> ListResult<T> error(String message) {
        ListResult<T> result = new ListResult<T>();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

    public static <T> ListResult<T> error(BizErrorEnum bizErrorEnum) {
        ListResult<T> result = new ListResult<T>();
        result.setSuccess(false);
        result.setErrorCode(bizErrorEnum.getCode());
        result.setMessage(bizErrorEnum.getDesc());
        return result;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
