package com.dbframe.core;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.dbframe.dao.PrepareDao;
import com.dbframe.model.SqlFilter;
import com.dbframe.script.DbInnerValue;
import com.dbframe.spring.SqlTemplateContextAware;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dbframe.po.MysqlShardTestPO;
import com.dbframe.po.TestPO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:spring.xml"
})
public class ShardTableTest implements SqlTemplateContextAware {

    private SqlTemplate<MysqlShardTestPO> template;
    private SqlTemplate<TestPO> template2;

    @Override
    public void setSqlTemplateContext(SqlTemplateContext SqlTemplateContext) {
        template = SqlTemplateContext.getSqlTemplate(MysqlShardTestPO.class);
        template2 = SqlTemplateContext.getSqlTemplate(TestPO.class);
    }

    @Autowired
    private PrepareDao prepareDao;

    private Date begin;
    private Date end;

    /**
     * 表清空，预设10条数据
     */
    @Before
    public void setUp() throws ParseException {
        // prepareDao.clear();
        prepareDao.clear000();
        prepareDao.clear001();
        prepareDao.clear002();
        prepareDao.clear003();
    }

    @Test
    public void insertTest() {
        System.out.println("" + null);

        MysqlShardTestPO testPO = new MysqlShardTestPO();
        for (int i = 10; i < 20; i++) {
            testPO = new MysqlShardTestPO();
            testPO.setDecimalVar(new BigDecimal(123.06));
            testPO.setFloatVar(5.16f);
            testPO.setDoubleVar(1234.6789);
            testPO.setTimeVar(DbInnerValue.getDbTime());
            testPO.setIntVar(i);
            int r = template.insert(testPO);
            Assert.assertEquals(1, r);// 未指定ID插入成功
            Assert.assertNotNull(testPO.getId());// 获得自增ID
        }

        SqlFilter filter = SqlFilter.init("intVar", 13);
        MysqlShardTestPO result = template.getUnique(filter);
        System.out.println(result);
        result.setDecimalVar(new BigDecimal(1203.06));
        result.setFloatVar(5.106f);
        result.setDoubleVar(1234.06789);
        result.setTimeVar(DbInnerValue.getDbTime());
        result.setStringVar("testPO1");
        template.update(result);

        TestPO testPO2 = new TestPO();
        testPO2.setDecimalVar(new BigDecimal(123.06));
        testPO2.setIntVar(798);
        testPO2.setFloatVar(5.16f);
        testPO2.setDoubleVar(1234.6789);
        testPO2.setTimeVar(DbInnerValue.getDbTime());
        int i = template2.insert(testPO2);
        Assert.assertEquals(1, i);// 未指定ID插入成功
        Assert.assertNotNull(testPO2.getId());// 获得自增ID

        // testPO.setId(88);
        // i = template.insert(testPO);
        // Assert.assertEquals(1, i);// 指定ID插入成功
        // Assert.assertEquals(88, testPO.getId().intValue());// ID不变
    }

}
