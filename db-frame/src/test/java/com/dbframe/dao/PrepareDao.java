package com.dbframe.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import com.dbframe.po.TestPO;

public interface PrepareDao {

    
   @Insert("insert into test(id,int_var,string_var,time_var) " +
   		"value (#{id},#{intVar},#{stringVar},#{timeVar})")
   void initData(TestPO testPO);

    @Delete("delete from test")
    void clear();

    @Delete("delete from test_000")
    void clear000();

    @Delete("delete from test_001")
    void clear001();

    @Delete("delete from test_002")
    void clear002();

    @Delete("delete from test_003")
    void clear003();
    
}
