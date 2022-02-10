package com.godric.cd.result;

import com.godric.cd.exception.BizErrorEnum;

import java.util.List;

public class PaginationResult<T> extends ListResult<T> {

    private int currentPage;

    private int pageSize;

    public static <T> PaginationResult<T> success(List<T> data, int totalCount, int currentPage, int pageSize) {
        PaginationResult<T> result = new PaginationResult<T>();
        result.setSuccess(true);
        result.setData(data);
        result.setCurrentPage(currentPage);
        result.setPageSize(pageSize);
        result.setTotalCount(totalCount);

        return result;
    }

    public static <T> PaginationResult<T> buildError() {
        PaginationResult<T> result = new PaginationResult<T>();
        result.setSuccess(false);
        return result;
    }

    public static <T> PaginationResult<T> buildError(String message) {
        PaginationResult<T> result = new PaginationResult<T>();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

    public static <T> PaginationResult<T> buildError(BizErrorEnum bizErrorEnum) {
        PaginationResult<T> result = new PaginationResult<T>();
        result.setSuccess(false);
        result.setErrorCode(bizErrorEnum.getCode());
        result.setMessage(bizErrorEnum.getDesc());
        return result;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
