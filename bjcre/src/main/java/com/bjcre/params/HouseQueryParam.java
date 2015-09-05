package com.bjcre.params;

import com.dbframe.enums.SortType;

public class HouseQueryParam {

    private Integer areaId;

    private Integer acreageMin;
    private Integer acreageMax;

    private Integer dayRentMin;
    private Integer dayRentMax;

    private int pageNo;
    private int limit = 20;
    private String sort;
    private SortType sortType;


    public Integer getDayRentMax() {
        return dayRentMax;
    }

    public void setDayRentMax(Integer dayRentMax) {
        this.dayRentMax = dayRentMax;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getAcreageMin() {
        return acreageMin;
    }

    public void setAcreageMin(Integer acreageMin) {
        this.acreageMin = acreageMin;
    }

    public Integer getAcreageMax() {
        return acreageMax;
    }

    public void setAcreageMax(Integer acreageMax) {
        this.acreageMax = acreageMax;
    }

    public Integer getDayRentMin() {
        return dayRentMin;
    }

    public void setDayRentMin(Integer dayRentMin) {
        this.dayRentMin = dayRentMin;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }



    @Override
    public String toString() {
        return "HouseQueryParam{" +
                "areaId=" + areaId +
                ", acreageMin=" + acreageMin +
                ", acreageMax=" + acreageMax +
                ", dayRentMin=" + dayRentMin +
                ", dayRentMax=" + dayRentMax +
                ", sort='" + sort + '\'' +
                ", sortType=" + sortType +
                '}';
    }
}