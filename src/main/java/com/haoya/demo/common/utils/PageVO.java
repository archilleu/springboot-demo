package com.haoya.demo.common.utils;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageVO<T> {

    public PageVO() {
    }

    public PageVO(Page<T> page) {
        this.count = page.getTotalElements();
        this.pages = page.getTotalPages();
        this.list = page.getContent();
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    private long pages;
    private long count;
    private List<T> list;
}
