package com.bjcre.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjcre.dao.HouseInfoDao;
import com.bjcre.params.HouseAddParam;
import com.bjcre.params.HouseQueryParam;
import com.bjcre.po.HouseInfoPo;
import com.bjcre.vo.HouseInfoVo;
import com.dbframe.enums.MatchType;
import com.dbframe.enums.SortType;
import com.dbframe.model.SqlFilter;
import com.dbframe.model.SqlSorter;

/**
 * Created by aohong on 15/8/29.
 */
@Service
public class HouseInfoBo {

    @Autowired
    private HouseInfoDao houseInfoDao;

    public HouseInfoVo transfer(HouseInfoPo po) {
        HouseInfoVo result =new HouseInfoVo();
        result.setId(po.getId());
        result.setConnectorMobile(po.getConnectorMobile());
        result.setConnector(po.getConnector());
        result.setAreaId(po.getAreaId());
        result.setBlockId(po.getBlockId());
        result.setAcreage(po.getAcreage());
        result.setAddress(po.getAddress());
        result.setDayRent(po.getDayRent());
        result.setDescription(po.getDescription());
        result.setInfoType(po.getInfoType());
        result.setImage(po.getImage());
        result.setIsRegisterCompany(po.getIsRegisterCompany());
        result.setHouseType(po.getHouseType());
        result.setHouseName(po.getHouseName());
        result.setTotalRent(po.getTotalRent());
        result.setTitle(po.getTitle());
        result.setCreateTime(po.getCreateTime());

        return result;
    }
    
    public HouseInfoVo add(HouseAddParam param) {
        HouseInfoPo po = new HouseInfoPo();
        po.setConnectorMobile(param.getConnectorMobile());
        po.setConnector(param.getConnector());
        po.setAreaId(param.getAreaId());
        po.setBlockId(param.getBlockId());
        po.setAcreage(param.getAcreage());
        po.setAddress(param.getAddress());
        po.setDayRent(param.getDayRent());
        po.setDescription(param.getDescription());
        po.setInfoType(param.getInfoType());
        po.setImage(param.getImage());
        po.setIsRegisterCompany(param.getIsRegisterCompany());
        po.setHouseType(param.getHouseType());
        po.setHouseName(param.getHouseName());
        po.setTotalRent(param.getTotalRent());
        po.setTitle(param.getTitle());
        po.setCreateTime(new Date());

        houseInfoDao.insert(po);
        return this.transfer(po);
    }

    public HouseInfoVo get(int id) {
        return this.transfer(houseInfoDao.getUniqueById(id));
    }

    public List<HouseInfoVo> query(HouseQueryParam param){
        List<HouseInfoVo> result=new ArrayList<HouseInfoVo>();

        List<HouseInfoPo> houseInfoPoList = houseInfoDao.pageEntities(
                this.getSqlFilter(param), SqlSorter.init("update_time", SortType.DESC), param.getPageNo(),param.getLimit());
        for (HouseInfoPo po : houseInfoPoList) {
            result.add(transfer(po));
        }
        return result;
    }

    public int count(HouseQueryParam param) {
        return houseInfoDao.count(this.getSqlFilter(param));
    }

    private SqlFilter getSqlFilter(HouseQueryParam param) {
        SqlFilter sqlFilter = SqlFilter.init();
        if (param != null) {
            if (param.getAreaId() != null) {
                sqlFilter.and("area_id", param.getAreaId());
            }
            if (param.getBlockId() != null) {
                sqlFilter.and("block_id", param.getBlockId());
            }
            if (param.getDayRentMin() != null) {
                sqlFilter.and("day_rent", param.getDayRentMin(), MatchType.GE);
            }
            if (param.getDayRentMax() != null) {
                sqlFilter.and("day_rent", param.getDayRentMax(), MatchType.LE);
            }
            if (param.getAcreageMin() != null) {
                sqlFilter.and("acreage", param.getAcreageMin(), MatchType.GE);
            }
            if (param.getAcreageMax() != null) {
                sqlFilter.and("acreage", param.getAcreageMax(), MatchType.LE);
            }
        }
        return sqlFilter;
    }
}
