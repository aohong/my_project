package com.bjcre.vo;

import com.dbframe.annotation.Id;
import com.dbframe.annotation.Table;

import java.util.Date;

/**
 * Created by aohong on 15/5/24.
 */
public class HouseInfoVo {

    private Integer id;
    private Integer infoType;
    private String title;
    private String houseName;
    private Integer areaId;
    private Integer blockId;
    private String address;
    private Integer houseType;
    private Integer isRegisterCompany;
    private Double dayRent;
    private Double totalRent;
    private Double acreage;
    private String connector;
    private String connectorMobile;
    private String description;
    private String image;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public Integer getIsRegisterCompany() {
        return isRegisterCompany;
    }

    public void setIsRegisterCompany(Integer isRegisterCompany) {
        this.isRegisterCompany = isRegisterCompany;
    }

    public Double getDayRent() {
        return dayRent;
    }

    public void setDayRent(Double dayRent) {
        this.dayRent = dayRent;
    }

    public Double getTotalRent() {
        return totalRent;
    }

    public void setTotalRent(Double totalRent) {
        this.totalRent = totalRent;
    }

    public Double getAcreage() {
        return acreage;
    }

    public void setAcreage(Double acreage) {
        this.acreage = acreage;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getConnectorMobile() {
        return connectorMobile;
    }

    public void setConnectorMobile(String connectorMobile) {
        this.connectorMobile = connectorMobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "HouseInfoVo{" +
                "id=" + id +
                ", infoType=" + infoType +
                ", title='" + title + '\'' +
                ", houseName='" + houseName + '\'' +
                ", areaId=" + areaId +
                ", blockId=" + blockId +
                ", address='" + address + '\'' +
                ", houseType=" + houseType +
                ", isRegisterCompany=" + isRegisterCompany +
                ", dayRent=" + dayRent +
                ", totalRent=" + totalRent +
                ", acreage=" + acreage +
                ", connector='" + connector + '\'' +
                ", connectorMobile='" + connectorMobile + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}