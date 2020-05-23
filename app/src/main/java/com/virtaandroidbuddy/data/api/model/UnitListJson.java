package com.virtaandroidbuddy.data.api.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UnitListJson implements Serializable {

    private long mCount;
    private long mPage;
    private long mPageSize;
    private List<String> mSort;
    private List<UnitListDataJson> mData;

    public long getCount() {
        return mCount;
    }

    public void setCount(long count) {
        mCount = count;
    }

    public long getPage() {
        return mPage;
    }

    public void setPage(long page) {
        mPage = page;
    }

    public long getPageSize() {
        return mPageSize;
    }

    public void setPageSize(long pageSize) {
        mPageSize = pageSize;
    }

    public List<String> getSort() {
        return mSort;
    }

    public void setSort(List<String> sort) {
        mSort = sort;
    }

    public List<UnitListDataJson> getData() {
        if (mData == null) {
            return new ArrayList<>();
        } else {
            return mData;
        }
    }

    public void setData(List<UnitListDataJson> data) {
        mData = data;
    }
}

