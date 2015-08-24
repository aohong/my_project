package com.dbframe;

import com.dbframe.basepo.ShardTable;
import com.dbframe.po.MysqlShardTestPO;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring.xml"
})
public class TestXML {

    @Test
    public void test() {
        StringUtils.isBlank("");
    }

}
