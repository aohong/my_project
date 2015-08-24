package com.dbframe.basepo;

import com.dbframe.model.SqlFilter;

/**
 * 需要分表的PO要实现该接口
 * 
 * @author aohong 2014-7-7 上午9:03:31
 */
public interface ShardTable {

    /**
     * 取得分表后缀，insert、update分表方式
     * 
     * @return 分表后缀
     */
    public String shardTableSuffix();

    /**
     * 取得分表后缀，查询单条sql
     * 
     * @return 分表后缀
     */
    public String shardTableSuffix(SqlFilter filter);

}
