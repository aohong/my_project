package test;

import java.util.Date;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.lang.StringUtils;

/**
 * 一般性测试代码，非测试用例
 */
public class CommonTest {

    @Test
    public void test() {
        System.out.println(new Date(1383580800000L));
        System.out.println(new Date());
        System.out.println(new Date(new Date().getTime() / 24 / 60 / 60 / 1000
                * 24 * 60 * 60 * 1000 - 8 * 3600 * 1000));
        Date dailySettleDate = new Date(new Random().nextLong()
                % new Date().getTime());
        // dailySettleDate.setDate(new Random().nextInt());
        System.out.println(dailySettleDate);
    }

    @Test
    public void StringFormat() {
        System.out.println(String.format("%s", "sss"));
        System.out.println(String.format("%s"));
    }

    @Test
    public void test2() throws ClassNotFoundException {
        // 引用biz工程src/test/java里的类，必须要手动在工程的Java构建路径的project里加上biz工程
        // com.wanda.settle.platform.biz.SpDailySettlePoProcessorTest test = new
        // com.wanda.settle.platform.biz.SpDailySettlePoProcessorTest();
        // System.out.println(test);
    }

    @Test
    public void test3() {
        Assert.fail();
    }

    @Test
    public void test4() {
//    	boolean a =true,b=false;
//    	int s =  a*10 + b;
//    	System.out.println(s);
//    	switch(s) {
//    	
//    	}
    }

    @Test
    public void test5() {
        StringUtils.isBlank("");
//String StingA;
    }

    @Test
    public void test6() {
        System.out.println(java.sql.Date.valueOf("2015-1-20"));
        System.out.println((new java.sql.Date(new Date().getTime()).getTime() -
                java.sql.Date.valueOf("2015-1-20").getTime())/1000/24/3600);
    }

}
