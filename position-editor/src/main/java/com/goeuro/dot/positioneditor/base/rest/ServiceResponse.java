package com.goeuro.dot.positioneditor.base.rest;

import java.io.Serializable;

public class ServiceResponse<R> implements Serializable {

    private R value;
    private String error;
    private boolean success;

    //used in pagination
    private int pageIndex;
    private int pageSize;
    private long totalItems;

    private ServiceResponse() {
    }

    public R getValue() {
        return value;
    }

    public ServiceResponse setValue(R value) {
        this.value = value;

        return this;
    }

    public String getError() {
        return error;
    }

    public ServiceResponse setError(String error) {
        this.error = error;

        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ServiceResponse setSuccess(boolean success) {
        this.success = success;

        return this;
    }

    public static <R> ServiceResponse<R> success(R value, int pageIndex, int pageSize, long totalItems) {
        return new ServiceResponse().setValue(value).setSuccess(true).setPageIndex(pageIndex).setPageSize(pageSize).setTotalItems(totalItems);
    }

    public static <R> ServiceResponse<R> success(R value) {
        return new ServiceResponse().setValue(value).setSuccess(true);
    }

    public static ServiceResponse faild(Exception ex) {
        return new ServiceResponse().setSuccess(false).setError(ex.getMessage());
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public ServiceResponse setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;

        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public ServiceResponse setPageSize(int pageSize) {
        this.pageSize = pageSize;

        return this;
    }


    public long getTotalItems() {
        return totalItems;
    }

    public ServiceResponse setTotalItems(long totalItems) {
        this.totalItems = totalItems;

        return this;
    }
}
