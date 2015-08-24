package com.dbframe;

import org.junit.Test;

import com.dbframe.basepo.ShardTable;
import com.dbframe.po.MysqlShardTestPO;

public class TempTest {

    @Test
    public void test() {
        System.out.println(MysqlShardTestPO.class
                .isAssignableFrom(ShardTable.class));
        System.out.println(ShardTable.class
                .isAssignableFrom(MysqlShardTestPO.class));
    }

}
