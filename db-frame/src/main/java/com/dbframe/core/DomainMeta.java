package com.dbframe.core;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dbframe.annotation.Column;
import com.dbframe.annotation.MapToUnderscore;
import com.dbframe.annotation.Version;
import com.dbframe.support.Constants;
import org.apache.commons.lang.StringUtils;

import com.dbframe.annotation.Id;
import com.dbframe.annotation.Ignore;
import com.dbframe.annotation.Scheme;
import com.dbframe.annotation.Table;
import com.dbframe.support.ValueExprHelper;

/**
 * 解析和缓存Domain类的元数据
 * 
 * @author leyuanren 2013-7-12 下午5:07:56
 */
public class DomainMeta {

    private Class<?> domainClass;
    private Map<String, Modifier> propertyMap = new HashMap<String, Modifier>();
    private Map<String, Modifier> columnMap = new HashMap<String, Modifier>();
    private List<Modifier> modifiers = new ArrayList<Modifier>();

    private String[] properties;
    private String[] columns;
    private String fullColumnStr = "";
    private String fullUpdateStr = "";

    private String schemeName;
    private String tableName;
    /**
     * 分表后缀
     */
    private ThreadLocal<String> shardTableSuffixLocal = new ThreadLocal<String>();
    // private String shardTableSuffix = "";
    private String idName; // id列的属性名
    private String versionName;
    private boolean mapToUnderscore = true;// 是否将java驼峰格式映射成数据库下划线格式

    private String selectStatement;
    private boolean selectBefore = true;

    private static Map<Class<?>, DomainMeta> domainCache = new ConcurrentHashMap<Class<?>, DomainMeta>();

    private class Modifier {
        public String property;
        public String column;
        public boolean ignoreNull;
        public Method getter;
        public Method setter;
    }

    public static DomainMeta getMeta(Class<?> clazz) {
        DomainMeta holder;
        if (domainCache.get(clazz) == null) {
            if (clazz != null && clazz.isAnnotationPresent(Table.class)) {
                holder = new DomainMeta(clazz);
                // 强制要求domain类有id注解
                if (holder.getIdName() == null) {
                    throw new RuntimeException(
                            "缺少@Id注解，Domain类必须要有一个标注了@Id的get方法，以映射数据库的主键字段! Domain Class Type:"
                                    + clazz.getName());
                }
                domainCache.put(clazz, holder);
            } else {
                throw new RuntimeException(
                        "缺少@Table注解，Domain类上必须标注@Table，以指定映射数据库中的哪个表! Domain Class Type:"
                                + clazz.getName());
            }
        } else {
            holder = domainCache.get(clazz);
        }
        return holder;
    }

    private DomainMeta(Class<?> domainClass) {
        MapToUnderscore mapToUnderscoreAnno = (MapToUnderscore) domainClass
                .getAnnotation(MapToUnderscore.class);
        if (mapToUnderscoreAnno != null) {
            mapToUnderscore = mapToUnderscoreAnno.value();
        }
        Table tableAnno = (Table) domainClass.getAnnotation(Table.class);
        this.tableName = tableAnno.value();
        this.domainClass = domainClass;
        dealSheme();

        init();
        properties = new String[modifiers.size()];
        columns = new String[modifiers.size()];
        for (int i = 0; i < modifiers.size(); i++) {
            properties[i] = modifiers.get(i).property;
            columns[i] = modifiers.get(i).column;
            if (i > 0) {
                fullColumnStr += ",";
            }
            fullColumnStr += columns[i];
            // 更新语句排除版本字段
            if (columns[i] != versionName) {
                if (fullUpdateStr.length() > 0) {
                    fullUpdateStr += ",";
                }
                fullUpdateStr = fullUpdateStr + columns[i] + "="
                        + getSingleUpdateExpr(properties[i], null);
            }

        }
    }

    private void init() {
        Method[] methods = domainClass.getMethods();
        // 先处理get方法
        for (Method method : methods) {
            // 不是Object类的方法
            if (method.getDeclaringClass() != Object.class) {
                String name = method.getName();
                String paramName = null;
                // get开头且无参数的
                if (name.length() > 3 && name.startsWith("get")
                        && method.getParameterTypes().length == 0) {
                    if (!method.isAnnotationPresent(Ignore.class)) {
                        Modifier modifier = new Modifier();
                        paramName = lowerFirstChar(name.substring(3));
                        modifier.property = paramName;
                        modifier.getter = method;

                        Column columnAnno = (Column) method
                                .getAnnotation(Column.class);
                        if (columnAnno != null) {
                            if (!columnAnno.name().equals("")) {
                                modifier.column = columnAnno.name()
                                        .toLowerCase();
                            } else {
                                modifier.column = getColumnName(paramName)
                                        .toLowerCase();
                            }
                            modifier.ignoreNull = columnAnno.ignoreNull();
                        } else {
                            modifier.column = getColumnName(paramName)
                                    .toLowerCase();
                            modifier.ignoreNull = true;
                        }
                        if (idName == null
                                && method.isAnnotationPresent(Id.class)) {
                            idName = modifier.property;
                            Id idAnno = method.getAnnotation(Id.class);
                            selectStatement = idAnno.select();
                            selectBefore = idAnno.before();
                            if ("".equals(selectStatement.trim())) {
                                selectStatement = null;
                            }
                        }
                        if (versionName == null
                                && method.isAnnotationPresent(Version.class)) {
                            versionName = modifier.column;
                        }
                        modifiers.add(modifier);
                        propertyMap.put(paramName, modifier);
                        columnMap.put(modifier.column, modifier);
                    }
                }
            }
        }

        // 再处理set方法
        for (Method method : methods) {
            // 不是Object类的方法
            if (method.getDeclaringClass() != Object.class) {
                String name = method.getName();
                String paramName = null;
                // set开头且只有1个参数的
                if (name.length() > 3 && name.startsWith("set")
                        && method.getParameterTypes().length == 1) {
                    paramName = lowerFirstChar(name.substring(3));
                    if (propertyMap.get(paramName) != null) {
                        propertyMap.get(paramName).setter = method;
                    }
                }
            }
        }
    }

    public String[] getAllProperties() {
        return properties;
    }

    private String lowerFirstChar(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    public Class<?> getDomainClass() {
        return domainClass;
    }

    public String getTableName() {
        return tableName;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public String getIdName() {
        return idName;
    }

    public String getIdColumn() {
        return propertyToColumn(idName);
    }

    public String getVersionName() {
        return versionName;
    }

    public boolean isIgnoreNull(String property) {
        return propertyMap.get(property).ignoreNull;
    }

    public boolean hasProperty(String property) {
        return propertyMap.get(property) != null;
    }

    public String propertyToColumn(String property) {
        return propertyMap.get(property) == null ? null : propertyMap
                .get(property).column;
    }

    public String columnToProperty(String column) {
        return columnMap.get(column) == null ? null
                : columnMap.get(column).property;
    }

    public String getFullColumnStr() {
        return fullColumnStr;
    }

    public String getFullUpdateStr() {
        return fullUpdateStr;
    }

    public String getSelectStatement() {
        return selectStatement;
    }

    public boolean isSelectBefore() {
        return selectBefore;
    }

    public String getSingleUpdateExpr(String property, Object value) {
        return ValueExprHelper.getValueExpr(Constants.UPDATE_BEAN_NAME + "."
                + property, value);
    }

    public String getFullTableName() {
        String tableName = this.tableName;

        if (schemeName != null) {
            tableName = schemeName + "." + tableName;
        }

        if (shardTableSuffixLocal.get() != null) {
            tableName += shardTableSuffixLocal.get();
        }
        return tableName;
    }

    public Object getValue(Object target, String property) {
        try {
            return propertyMap.get(property).getter.invoke(target);
        } catch (Exception e) {
            throw new RuntimeException("获取domain对象的属性值失败。", e);
        }
    }

    public void setValue(Object target, String property, Object value) {
        try {
            Method method = propertyMap.get(property).setter;
            if (method != null) {
                Object valueObj = value;
                if (value != null) {
                    String valueStr = value.toString();
                    Class<?> paramType = method.getParameterTypes()[0];
                    if (paramType == String.class) {
                        valueObj = valueStr;
                    } else if ((paramType == Integer.class || paramType == int.class)) {
                        if (!(value instanceof Integer)) {
                            valueObj = Integer.valueOf(valueStr);
                        }
                    } else if ((paramType == Double.class || paramType == double.class)) {
                        if (!(value instanceof Double)) {
                            valueObj = Double.valueOf(valueStr);
                        }
                    } else if ((paramType == Long.class || paramType == long.class)) {
                        if (!(value instanceof Long)) {
                            valueObj = Long.valueOf(valueStr);
                        }
                    } else if ((paramType == Float.class || paramType == float.class)) {
                        if (!(value instanceof Float)) {
                            valueObj = Float.valueOf(valueStr);
                        }
                    } else if ((paramType == Boolean.class || paramType == boolean.class)) {
                        if (!(value instanceof Boolean)) {
                            if ("true".equalsIgnoreCase(valueStr)) {
                                valueObj = Boolean.TRUE;
                            } else if ("false".equalsIgnoreCase(valueStr)) {
                                valueObj = Boolean.FALSE;
                            }
                        }
                    } else if (paramType == BigDecimal.class) {
                        if (!(value instanceof BigDecimal)) {
                            valueObj = new BigDecimal(valueStr);
                        }
                    }
                    method.invoke(target, valueObj);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("设置domain对象的属性值失败。", e);
        }
    }

    private void dealSheme() {
        String sheme = null;
        Scheme schemeAnno = (Scheme) domainClass.getAnnotation(Scheme.class);
        if (schemeAnno != null) {
            sheme = schemeAnno.name();
        }
        if (StringUtils.isNotBlank(sheme)) {
            this.schemeName = sheme;
        }

    }

    private String getColumnName(String propertyName) {
        if (mapToUnderscore) {
            // 将aaBbbCcc转换为aa_bbb_ccc格式
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < propertyName.length(); i++) {
                char c = propertyName.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    buffer.append("_").append((char) (c + 32));
                } else {
                    buffer.append(c);
                }
            }
            return buffer.toString();
        } else {
            // 否则不做处理,原样转换
            return propertyName;
        }

    }

    public String getShardTableSuffix() {
        String suffix = shardTableSuffixLocal.get();

        if (suffix == null) {
            suffix = "";
        }

        return suffix;
    }

    public void setShardTableSuffix(String shardTableSuffix) {
        // this.shardTableSuffix = shardTableSuffix;
        shardTableSuffixLocal.set(shardTableSuffix);
    }

}
