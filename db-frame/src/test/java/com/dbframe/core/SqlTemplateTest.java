package com.dbframe.core;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dbframe.dao.PrepareDao;
import com.dbframe.enums.SortType;
import com.dbframe.spring.SqlTemplateContextAware;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dbframe.enums.MatchType;
import com.dbframe.model.SqlFilter;
import com.dbframe.model.SqlSorter;
import com.dbframe.po.TestPO;
import com.dbframe.script.DbInnerValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:spring.xml"
})
public class SqlTemplateTest implements SqlTemplateContextAware {

    private SqlTemplate<TestPO> template;

    @Override
    public void setSqlTemplateContext(SqlTemplateContext SqlTemplateContext) {
        template = SqlTemplateContext.getSqlTemplate(TestPO.class);
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
        prepareDao.clear();
        for (int i = 1; i <= 4; i++) {
            TestPO testPO = new TestPO();
            testPO.setId(i);
            testPO.setStringVar("test" + i);
            testPO.setIntVar(1);
            testPO.setTimeVar(DbInnerValue.getDbTime());
            prepareDao.initData(testPO);
        }
        for (int i = 1; i <= 6; i++) {
            TestPO testPO = new TestPO();
            testPO.setId(i + 4);
            testPO.setStringVar("test" + i);
            testPO.setIntVar(2);
            testPO.setTimeVar(new Date());
            prepareDao.initData(testPO);
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        begin = df.parse("2013-07-01");
        end = new Date();
    }

    @Test
    public void getIdTest() {
        TestPO testPO = new TestPO();
        testPO.setId(123);
        Object obj = template.getId(testPO);
        Assert.assertEquals(123, obj);
    }

    @Test
    public void insertTest() {

        TestPO testPO = new TestPO();
        testPO.setDecimalVar(new BigDecimal(123.06));
        testPO.setIntVar(798);
        testPO.setFloatVar(5.16f);
        testPO.setDoubleVar(1234.6789);
        testPO.setTimeVar(DbInnerValue.getDbTime());
        int i = template.insert(testPO);
        Assert.assertEquals(1, i);// 未指定ID插入成功
        Assert.assertNotNull(testPO.getId());// 获得自增ID

        testPO.setId(88);
        testPO.setTimeVar(new Date());
        i = template.insert(testPO);
        Assert.assertEquals(1, i);// 指定ID插入成功
        Assert.assertEquals(88, testPO.getId().intValue());// ID不变
    }

    @Test
    public void ignoreInsertTest() {
        TestPO testPO = new TestPO();
        testPO.setId(1);
        int i = template.ignoreInsert(testPO);
        Assert.assertEquals(0, i);// 忽略主键重复，插入0条
    }

    @Test
    public void batchInsertTest() {
        List<TestPO> poList = new ArrayList<TestPO>();
        for (int i = 0; i < 30; i++) {
            TestPO testPO = new TestPO();
            testPO.setIntVar(i + 1000);
            poList.add(testPO);
        }
        int i = template.batchInsert(poList);
        Assert.assertEquals(30, i);// 在一条SQL中一次性插入30条
        for (TestPO po : poList) {
            Assert.assertNotNull(po.getId());// 获得自增ID
        }
    }

    @Test
    public void batchIgnoreInsertTest() {
        List<TestPO> poList = new ArrayList<TestPO>();
        for (int i = 0; i < 10; i++) {
            TestPO testPO = new TestPO();
            testPO.setId(i + 1);
            testPO.setIntVar(i + 1000);
            poList.add(testPO);
        }
        for (int i = 0; i < 20; i++) {
            TestPO testPO = new TestPO();
            testPO.setIntVar(i + 1000);
            poList.add(testPO);
        }
        for (int i = 0; i < 10; i++) {
            TestPO testPO = new TestPO();
            testPO.setId(i + 1);
            testPO.setIntVar(i + 1000);
            poList.add(testPO);
        }
        int i = template.batchIgnoreInsert(poList);
        Assert.assertEquals(20, i);// 提交40条数据，20条主键重复被忽略，实际插入成功20条
        for (TestPO po : poList) {
            Assert.assertNotNull(po.getId());// 获得自增ID
        }
        printAll(poList);
    }

    @Test
    public void countTest() {
        int count = template.count(SqlFilter.init("intVar", 2));
        Assert.assertEquals(6, count);// 获得count
    }

    @Test
    public void queryUniqueTest() {
        TestPO testPO = template.getUniqueById(1);
        Assert.assertNotNull(testPO);
        Assert.assertEquals(1, testPO.getId().intValue());
        Assert.assertEquals(1, testPO.getIntVar().intValue());
        Assert.assertEquals("test1", testPO.getStringVar());
        Assert.assertNotNull(testPO.getTimeVar());
    }

    @Test
    public void queryListTest() throws ParseException {
        // where time_var>begin and time_var<=end and string_var like 'test%'
        // and int_var in (2,3) and decimal_var is null order by id desc
        SqlFilter filter = SqlFilter.init("timeVar", begin, MatchType.GT)
                .and("timeVar", DbInnerValue.getDbTime(), MatchType.LE)
                .and("stringVar", "test%", MatchType.LIKE)
                .and("intVar", new Integer[] {
                        1, 3
                }, MatchType.IN).and("decimalVar", null);
        List<TestPO> list = template.queryEntities(filter,
                SqlSorter.init("id", SortType.DESC));
        Assert.assertNotNull(list);
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(4, list.get(0).getId().intValue());
        Assert.assertEquals(3, list.get(1).getId().intValue());
        Assert.assertEquals(2, list.get(2).getId().intValue());
        Assert.assertEquals(1, list.get(3).getId().intValue());
        printAll(list);
    }

    @Test
    public void queryListTest2() throws ParseException {
        // where time_var>begin and time_var<=now and (string_var='test3' or
        // id=4 or (id in (1,2,3,8,9) and int_var!=1)) order by id desc
        SqlFilter filter = SqlFilter.init("timeVar", begin, MatchType.GT).and(
                "timeVar", end, MatchType.LE);
        SqlFilter subFilter1 = SqlFilter.init("stringVar", "test3").or("id", 4);
        SqlFilter subFilter2 = SqlFilter.init("id", new int[] {
                1, 2, 3, 8, 9
        }, MatchType.IN).and("intVar", 1, MatchType.NE);

        filter.and(subFilter1.or(subFilter2));

        List<TestPO> list = template.queryEntities(filter,
                SqlSorter.init("id", SortType.DESC));
        Assert.assertNotNull(list);
        Assert.assertEquals(5, list.size());
        Assert.assertEquals(9, list.get(0).getId().longValue());
        Assert.assertEquals(8, list.get(1).getId().longValue());
        Assert.assertEquals(7, list.get(2).getId().longValue());
        Assert.assertEquals(4, list.get(3).getId().longValue());
        Assert.assertEquals(3, list.get(4).getId().longValue());

        list = template.pageEntities(filter,
                SqlSorter.init("id", SortType.DESC), 1, 2);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(8, list.get(0).getId().longValue());
        Assert.assertEquals(7, list.get(1).getId().longValue());

        printAll(list);
    }

    @Test
    public void queryMapTest() throws ParseException {
        // where time_var>begin and time_var<=now
        SqlFilter filter = SqlFilter.init("time_var", begin, MatchType.GT).and(
                "time_var", end, MatchType.LE);
        // select *
        List<Map<String, Object>> list1 = template.queryMaps(filter,
                SqlSorter.init("id", SortType.DESC));
        // select id,string_var
        List<Map<String, Object>> list2 = template.queryMaps(filter,
                SqlSorter.init("id", SortType.DESC), "id", "stringVar");
        // select count(id),count(distinct int_var),sum(int_var)
        List<Map<String, Object>> list3 = template.queryMaps(filter,
                SqlSorter.init("id", SortType.DESC), new String[] {
                        "count(id)", "count(distinct intVar)", "sum(intVar)"
                });
        // 验证结果
        Assert.assertEquals(10, list1.size());
        for (Map<String, Object> map : list1) {
            Assert.assertEquals(4, map.size());
            System.out.println(map.toString());
        }

        Assert.assertEquals(10, list2.size());
        for (Map<String, Object> map : list2) {
            Assert.assertEquals(2, map.size());
            System.out.println(map.toString());
        }

        Assert.assertEquals(1, list3.size());
        Map<String, Object> map = list3.get(0);
        Assert.assertEquals(3, map.size());
        Assert.assertEquals(10L, map.get("id_count"));
        Assert.assertEquals(2L, map.get("intVar_count_distinct"));
        Assert.assertEquals(new BigDecimal(16), map.get("intVar_sum"));
        System.out.println(map.toString());
    }

    @Test
    public void updateTest() {
        Date now = new Date();
        double db = 12.306;
        TestPO po = template.getUniqueById(1);
        po.setStringVar(now.toString());
        po.setTimeVar(DbInnerValue.getDbTime());
        po.setDoubleVar(db);
        int i = template.update(po);
        Assert.assertEquals(1, i);// 成功1条

        po.setTimeVar(DbInnerValue.getDbTime());
        i = template.update(po, "timeVar");// 只更新stringVar
        Assert.assertEquals(1, i);// 成功1条

        po = template.getUniqueById(1);
        Assert.assertEquals(now.toString(), po.getStringVar());// 字段stringVar已更新
        Assert.assertEquals(now.getTime() / 1000,
                po.getTimeVar().getTime() / 1000);// 字段timeVar已更新
        Assert.assertEquals(db, po.getDoubleVar(), 0);// 字段doubleVar已更新

        po.setStringVar("asd");
        po.setDoubleVar(123);
        i = template.update(po, "stringVar");// 只更新stringVar
        Assert.assertEquals(1, i);// 成功1条

        po = template.getUniqueById(1);
        Assert.assertEquals("asd", po.getStringVar());// 字段stringVar已更新
        Assert.assertEquals(db, po.getDoubleVar(), 0);// 字段doubleVar值不变
    }

    @Test
    public void deleteTest() {
        TestPO testPO = new TestPO();
        testPO.setId(10);
        int i = template.delete(testPO);
        Assert.assertEquals(1, i);// 删除单条记录成功
        testPO = template.getUniqueById(10);
        Assert.assertNull(testPO);
    }

    @Test
    public void queryMap2Test() {
        System.out.println(template.queryMaps(
                SqlFilter.init("id", 1, MatchType.GE), null,
                "decimalVar"));
        System.out.println(template.queryMaps(SqlFilter.init("id", 1), null,
                "id", "intVar", "decimalVar"));
        System.out.println(template.queryMaps(SqlFilter.init("id", 1), null,
                "id", "decimalVar"));

        System.out.println(template.querySingleColumn(SqlFilter.init("id", 1),
                null, "decimalVar", BigDecimal.class));
    }

    private void printAll(List<TestPO> list) {
        for (TestPO po : list) {
            print(po);
        }
    }

    private void print(TestPO domain) {
        DomainMeta meta = DomainMeta.getMeta(TestPO.class);
        StringBuffer buffer = new StringBuffer("Class name:")
                .append(TestPO.class.getName());
        buffer.append(" [");
        for (String property : meta.getAllProperties()) {
            Object value = meta.getValue(domain, property);
            buffer.append(property);
            if (value == null) {
                buffer.append(" is null; ");
            } else {
                buffer.append("=").append(value.toString()).append("; ");
            }
        }
        buffer.append("]");
        System.out.println(buffer.toString());
    }

}
