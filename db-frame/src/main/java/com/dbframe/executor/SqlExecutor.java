package com.dbframe.executor;

import java.util.List;
import java.util.Map;

import com.dbframe.support.Constants;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

/**
 * Mybatis Sql 执行引擎
 * @author leyuanren
 *
 */
public interface SqlExecutor {
	
final String SQL = "${"+ Constants.SQLDEFINITION_NAME+".sql}";
    
    @SelectKey(statement={"${"+Constants.GENERATE_KEY+".statment}"},
                keyProperty=Constants.GENERATE_KEY+".key",
                before=false,
                resultType=Object.class)
    @Insert(SQL)
    public int insert(@Param(Constants.SQLDEFINITION_NAME)SqlDefinition definition,
            @Param(Constants.GENERATE_KEY)GeneratedKey generateKey);
    
    @Update(SQL)
    public int update(@Param(Constants.SQLDEFINITION_NAME)SqlDefinition definition);
    
    @Delete(SQL)
    public int delete(@Param(Constants.SQLDEFINITION_NAME)SqlDefinition definition);
    
    @Select(SQL)
    public int queryCount(@Param(Constants.SQLDEFINITION_NAME)SqlDefinition definition);
    
    @Select(SQL)
    public List<Map<String,Object>> queryEntities(@Param(Constants.SQLDEFINITION_NAME)SqlDefinition definition);
    
    @Select("${sql}")
    public Object queryKey(@Param("sql")String sql);
    
}
