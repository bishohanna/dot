package com.goeuro.dot.base.model;

import java.io.Serializable;
import java.util.Collection;

public class PagedResponse<M extends BaseModel> implements Serializable {


    private Collection<M> items;
    private int pageIndex;
    private int pageSize;
    private Long totalItems;


    public Collection<M> getItems() {
        return items;
    }

    public PagedResponse setItems(Collection<M> items) {
        this.items = items;

        return this;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public PagedResponse setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;

        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PagedResponse setPageSize(int pageSize) {
        this.pageSize = pageSize;

        return this;
    }


    public Long getTotalItems() {
        return totalItems;
    }

    public PagedResponse setTotalItems(Long totalItems) {
        this.totalItems = totalItems;

        return this;
    }
}
