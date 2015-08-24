package com.dbframe.core;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dbframe.bo.TestBO;
import com.dbframe.po.TestPO;



public class BOTest {

    @Autowired
    private TestBO testBO;

    public void test(){
        
        List<TestPO> list = testBO.queryEntities();
        
        TestPO po = testBO.getUniqueById(1);
        
        testBO.insert(po);
        
        testBO.update(po, "id","name");
        
        testBO.delete(po);
    }

}
