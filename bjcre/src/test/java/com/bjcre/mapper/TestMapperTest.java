package com.bjcre.mapper;

import com.bjcre.po.TestPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by aohong on 15/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:dao.xml"
})
public class TestMapperTest {

    @Autowired
    private TestMapper testMapper;

    @Test
    public void testAddMappingInformation() throws Exception {
        TestPo testPo = new TestPo();
        testPo.setName("test");
        testMapper.insert(testPo);
    }
}