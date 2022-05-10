package com.sunc.cwy.model.common;


import java.io.Serializable;

/**
 * 抽象基类，供实体类继承
 */
public abstract class BaseDomain implements Serializable {


    private static final long serialVersionUID = -3308831596689250063L;
    private int start;
    private int limit = 20;
    private int end;
    private String sort;
    private String order;
    private String dir;

    public BaseDomain() {
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return this.dir;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
