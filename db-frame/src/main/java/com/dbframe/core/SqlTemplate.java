package com.dbframe.core;

import java.util.List;
import java.util.Map;

import com.dbframe.model.SqlFilter;
import com.dbframe.model.SqlSorter;

/**
 * 定义基于domain对象的通用的插入、更新、删除、查询操作,只支持单表操作，不支持级联，不支持分组查询等复杂功能
 * 
 * @author leyuanren
 */
public interface SqlTemplate<T> {

    /**
     * 获得实体对象的id值，基于domain类的@Id注解，如果id为空，返回null
     * 
     * @param entity
     *            实体对象
     * @return ID值
     */
    public Object getId(T entity);

    /**
     * 插入一条新记录，默认属性为NULL的属性不会出现在插入语句中
     * 
     * @param entity
     *            实体对象
     * @return 插入成功的条数
     */
    public int insert(T entity);

    /**
     * 插入一条新记录，插入时主键或唯一索引重复时不报错，而是忽略
     * 依赖SqlBuilder的实现，如果SqlBuilder实现不支持该语法，抛出UnsupportedOperationException异常
     * 
     * @param entity
     *            实体对象
     * @return 插入成功的条数
     */
    public int ignoreInsert(T entity);

    /**
     * 使用一条SQL批量插入多条记录,此时对象的所有属性都将写入SQL语句中
     * 依赖SqlBuilder的实现，如果SqlBuilder实现不支持该语法，抛出UnsupportedOperationException异常
     * 
     * @param entities
     *            实体对象列表
     * @return 插入成功的条数
     */
    public int batchInsert(List<T> entities);

    /**
     * 使用一条SQL批量插入多条记录,此时对象的所有属性都将写入SQL语句中,插入时主键或唯一索引重复时不报错，而是忽略
     * 依赖SqlBuilder的实现，如果SqlBuilder实现不支持该语法，抛出UnsupportedOperationException异常
     * 
     * @param entities
     *            实体对象列表
     * @return 插入成功的条数
     */
    public int batchIgnoreInsert(List<T> entities);

    /**
     * 更新一条记录指定的几列,entity对象必须标注了@Id且id属性值不能为空.
     * 
     * @param entity
     *            实体对象
     * @param properties
     *            String数组，每一个元素表示要更新列的列名，注意列名必须是实体类的的属性名(根据getXxx方法定义)
     *            如果找不到属性，会抛出异常。数组为空则表示更新全部字段
     * @return 更新成功的记录数
     */
    public int update(T entity, String... properties);

    /**
     * 删除一条记录,entity对象必须标注了@Id且id属性值不能为空
     * 
     * @param entity
     *            实体对象
     * @return 删除成功的记录数
     */
    public int delete(T entity);

    /**
     * 删除一条记录,entity对象必须标注了@Id且id属性值不能为空
     * 
     * @param id
     *            实体对象的id
     * @return 删除成功的记录数
     */
    public int deleteById(Object id);

    /**
     * 查询记录总数
     * 
     * @param filter
     *            封装过滤条件
     * @return 查出来的记录总数
     */
    public int count(SqlFilter filter);

    /**
     * 根据主键查询唯一对象,需要在实体类里标注@Id注解，如果查出来的结果数量>1返回第一个，查不到则返回NULL
     * 
     * @param id
     *            主键值
     * @return 查询结果自动映射为实体对象
     */
    public T getUniqueById(Object id);

    /**
     * 根据组合条件查询唯一对象,如果查出来的结果数量>1返回第一个，查不到则返回NULL
     * 
     * @param filter
     *            封装过滤条件
     * @return 查询结果自动映射为实体对象
     */
    public T getUnique(SqlFilter filter);

    /**
     * 查询对象列表
     * 
     * @param filter
     *            封装过滤条件
     * @param sorter
     *            封装排序规则
     * @return 查询结果自动映射为实体对象的列表
     */
    public List<T> queryEntities(SqlFilter filter, SqlSorter sorter);

    /**
     * 分页查询对象列表
     * 依赖SqlBuilder的实现，如果SqlBuilder实现不支持该语法，抛出UnsupportedOperationException异常
     * 
     * @param filter
     *            封装过滤条件
     * @param sorter
     *            封装排序规则
     * @param offset
     *            起始记录偏移量
     * @param limit
     *            分页的最大长度
     * @return 查询结果自动映射为实体对象的列表
     */
    public List<T> pageEntities(SqlFilter filter, SqlSorter sorter, int offset,
            int limit);

    /**
     * 查询对象列表，可自定义所要查询的列，返回的每一条记录用map表示
     * 
     * @param filter
     *            封装过滤条件
     * @param sorter
     *            封装排序规则
     * @param selects
     *            String数组，每一个元素表示要查询列的列名，注意列名必须是实体类的的属性名(根据getXxx方法定义)
     *            如果找不到属性，会抛出异常。
     *            目前也支持以下函数操作：sum(proprty)、count(proprty)、count(distinct
     *            proprty)
     *            查询结果集中将会被映射为proprty_sum、proprty_count、proprty_count_distinct元素
     *            其中property代表属性名称.例如可以这样构造select数组:
     *            String[] selects = new
     *            String[]{"count(id)","count(distinct uid)","sum(money)"};
     * @return
     */
    public List<Map<String, Object>> queryMaps(SqlFilter filter,
            SqlSorter sorter, String... selects);

    /**
     * 查询对象单个列的值列表，可自定义所要查询的列和映射的类型
     * 
     * @param filter
     *            封装过滤条件
     * @param sorter
     *            封装排序规则
     * @param select
     *            表示要查询列的列名，注意列名必须是实体类的的属性名(根据getXxx方法定义)
     *            如果找不到属性，会抛出异常。
     * @param type
     *            列对应的映射类型
     * @return
     */
    public <E> List<E> querySingleColumn(SqlFilter filter, SqlSorter sorter,
            String select, Class<E> type);

    /**
     * 分页查询对象列表，可自定义所要查询的列，返回的每一条记录用map表示
     * 依赖SqlBuilder的实现，如果SqlBuilder实现不支持该语法，抛出UnsupportedOperationException异常
     * 
     * @param filter
     *            封装过滤条件
     * @param sorter
     *            封装排序规则
     * @param offset
     *            起始记录偏移量
     * @param limit
     *            分页的最大长度
     * @param selects
     *            String数组，每一个元素表示要查询列的列名，注意列名必须是实体类的的属性名(根据getXxx方法定义)
     *            如果找不到属性，会抛出异常。
     *            目前也支持以下函数操作：sum(proprty)、count(proprty)、count(distinct
     *            proprty)
     *            查询结果集中将会被映射为proprty_sum、proprty_count、proprty_count_distinct元素
     *            其中property代表属性名称.例如可以这样构造select数组:
     *            String[] selects = new
     *            String[]{"count(id)","count(distinct uid)","sum(rrd)"};
     * @return
     */
    public List<Map<String, Object>> pageMaps(SqlFilter filter,
            SqlSorter sorter, int offset, int limit, String... selects);
}
