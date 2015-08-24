package com.dbframe.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dbframe.enums.SortType;
import com.dbframe.model.FilterProperty;
import com.dbframe.model.SortProperty;
import com.dbframe.model.SqlFilter;
import com.dbframe.model.SqlSorter;
import com.dbframe.support.CommonUtils;
import com.dbframe.support.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dbframe.enums.MatchType;
import com.dbframe.support.ValueExprHelper;

/**
 * wrap标记编译器
 * 
 * @author leyuanren 2013-7-5 上午11:16:03
 */
public class WrapTokenCompiler implements SqlCompiler {

    private static Log logger = LogFactory.getLog(WrapTokenCompiler.class);

    private static final Pattern PATTERN_WRAP = Pattern
            .compile("#wrap\\{(.*?)\\}");
    private static final Pattern PATTERN_VAR = Pattern.compile(":[^\\s]+");

    public String apply(String sql, Map<String, Object> parametersAsMap) {
        int position = 0;
        int length = sql.length();
        StringBuilder sqlBuffer = new StringBuilder(sql);
        Matcher wrapMatcher = PATTERN_WRAP.matcher(sql);
        List<Replacement> replacements = new ArrayList<Replacement>();

        while ((position < length) && wrapMatcher.find(position)) {
            Replacement rep = new Replacement(wrapMatcher.start(),
                    wrapMatcher.end());
            replacements.add(rep);
            position = wrapMatcher.end();
            String expr = wrapMatcher.group(1);
            String result = processWrapExpr(expr, parametersAsMap);
            if (result != null) {
                rep.replaced = result;
            }
        }
        for (int i = replacements.size() - 1; i >= 0; i--) {
            Replacement rep = replacements.get(i);
            sqlBuffer.replace(rep.start, rep.end, rep.replaced);
        }
        final String wrappedSql = sqlBuffer.toString();
        if (logger.isDebugEnabled()) {
            logger.debug("构造SQL语句:" + wrappedSql);
        }

        return wrappedSql;
    }

    private String processWrapExpr(String expr,
            Map<String, Object> parametersAsMap) {
        Matcher varMatcher = PATTERN_VAR.matcher(expr);
        if (varMatcher.find()) {
            String varName = varMatcher.group().substring(1);
            Object obj = parametersAsMap.get(varName);
            if (obj != null) {
                String compileredSql = null;
                if (obj instanceof SqlFilter) {
                    List<Object> vars = new ArrayList<Object>();
                    String varsName = varName + Constants.FILTER_VARS_NAME;
                    compileredSql = wrapFilters(varsName, (SqlFilter) obj, vars);
                    if (!vars.isEmpty()) {
                        parametersAsMap.put(varsName, vars);
                    }
                } else if (obj instanceof SqlSorter) {
                    compileredSql = wrapSorts((SqlSorter) obj);
                } else if (obj instanceof String) {
                    // 如果是string类型的 直接替换
                    compileredSql = (String) obj;
                } else if (obj instanceof Collection<?>) {
                    Collection<?> c = (Collection<?>) obj;
                    compileredSql = ValueExprHelper.getValueExpr(varName, 0,
                            ((Collection<?>) obj).toArray());
                } else if (obj.getClass().isArray()) {
                    if (obj instanceof Object[]) {
                        compileredSql = ValueExprHelper.getValueExpr(varName,
                                0, (Object[]) obj);
                    } else {
                        int len = CommonUtils.getArrayLength(obj);
                        compileredSql = ValueExprHelper.getValueExpr(varName,
                                len, 0);
                    }
                } else {
                    compileredSql = ValueExprHelper.getValueExpr(varName, obj);
                }

                if (compileredSql != null) {
                    return new StringBuilder(expr).replace(varMatcher.start(),
                            varMatcher.end(), compileredSql).toString();
                }
            }
        }
        return null;
    }

    private String wrapFilters(String varName, SqlFilter sqlFilter,
            List<Object> vars) {
        if (sqlFilter == null
                || (sqlFilter.getList().isEmpty() && sqlFilter.getGroupList()
                        .isEmpty())) return null;

        // 防止出现环状链接，递归陷入死循环
        Set<SqlFilter> processed = new HashSet<SqlFilter>();
        processed.add(sqlFilter);
        String wrapped = wrapFiltersRecursively(varName, sqlFilter, processed,
                vars);
        return wrapped;
    }

    private String wrapFiltersRecursively(String varName, SqlFilter filter,
            Set<SqlFilter> processed, List<Object> varList) {
        StringBuilder buffer = new StringBuilder();
        List<FilterProperty> list = filter.getList();
        for (int i = 0; i < list.size(); i++) {
            FilterProperty fp = list.get(i);
            String name = fp.getColumnName();
            Object value = fp.getPropertyValue();
            if (i > 0) {
                buffer.append(" ").append(fp.getJoinType().toString())
                        .append(" ");
            }
            if (value == null) {
                buildCondition(name, fp.getMatchType(), null, buffer);
            } else {
                String expr = handleValue(value, varName, varList);
                buildCondition(name, fp.getMatchType(), expr, buffer);
            }
        }

        List<SqlFilter> group = filter.getGroupList();
        // 递归处理
        for (int i = 0; i < group.size(); i++) {
            SqlFilter fps = group.get(i);
            if (!processed.contains(fps)) {
                processed.add(fps);
                String subStr = wrapFiltersRecursively(varName, fps, processed,
                        varList);
                if (StringUtils.isNotBlank(subStr)) {
                    if (buffer.length() > 0) {
                        buffer.append(" ").append(filter.getGroupJoinType(i));
                    }
                    buffer.append(" (").append(subStr).append(") ");
                }
            }
        }
        return buffer.toString();
    }

    /**
     * 返回 order by id,name desc,age 之类的排序语句
     */
    private String wrapSorts(SqlSorter sortProperties) {
        if (sortProperties == null || sortProperties.getList().isEmpty())
            return null;
        StringBuilder buffer = new StringBuilder();
        List<SortProperty> list = sortProperties.getList();
        for (int i = 0; i < list.size(); i++) {
            SortProperty sp = list.get(i);
            String column = sp.getColumnName();
            String order = sp.getSortType() == SortType.DESC ? " DESC" : " ASC";
            buffer.append(column).append(order);
            if (i < list.size() - 1) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }

    private void buildCondition(String name, MatchType matchType,
            String valueExp, StringBuilder buffer) {
        String extend = extendConditionHandle(name, matchType, valueExp);
        // 如果定义了额外的处理规则
        if (extend != null) {
            buffer.append(extend).append(" ");
            return;
        }
        // 否则使用默认的规则
        if (MatchType.EQ.equals(matchType)) {
            if (valueExp == null) {
                buffer.append(name).append(" IS NULL ");
            } else {
                buffer.append(name).append("=").append(valueExp);
            }
        } else if (MatchType.NE.equals(matchType)) {
            if (valueExp == null)
                buffer.append(name).append(" IS NOT NULL ");
            else
                buffer.append(name).append("!=").append(valueExp);
        } else if (MatchType.LIKE.equals(matchType)) {
            buffer.append(name).append(" LIKE ").append(valueExp);
        } else if (MatchType.LE.equals(matchType)) {
            buffer.append(name).append("<=").append(valueExp);
        } else if (MatchType.LT.equals(matchType)) {
            buffer.append(name).append("<").append(valueExp);
        } else if (MatchType.GE.equals(matchType)) {
            buffer.append(name).append(">=").append(valueExp);
        } else if (MatchType.GT.equals(matchType)) {
            buffer.append(name).append(">").append(valueExp);
        } else if (MatchType.IN.equals(matchType)) {
            buffer.append(name).append(" in (").append(valueExp).append(")");
        } else if (MatchType.NOT_IN.equals(matchType)) {
            buffer.append(name).append(" not in (").append(valueExp)
                    .append(")");
        } else {
            return;
        }
        buffer.append(" ");
    }

    /**
     * 钩子方法，子类可以实现该方法，扩展对某些matchType类型进行自定义的处理。
     * 这将覆盖父类默认的处理。
     * 如果处理不了，那么应该返回null
     * 
     * @param name
     * @param matchType
     * @param valueExp
     * @return
     */
    protected String extendConditionHandle(String name, MatchType matchType,
            String valueExp) {
        return null;
    }

    protected String handleValue(Object value, String varName,
            List<Object> varList) {
        if (value instanceof Collection<?>) {
            int offset = varList.size();
            varList.addAll((Collection<?>) value);
            return ValueExprHelper.getValueExpr(varName, offset,
                    ((Collection<?>) value).toArray());
        } else if (value.getClass().isArray()) {
            int offset = varList.size();
            Object[] array = CommonUtils.toObjectArray(value);
            Collections.addAll(varList, array);
            return ValueExprHelper.getValueExpr(varName, offset, array);
        } else {
            varList.add(value);
            varName = varName + "[" + (varList.size() - 1) + "]";
            return ValueExprHelper.getValueExpr(varName, value);
        }
    }

    private class Replacement {
        private int start;
        private int end;
        private String replaced = "";

        public Replacement(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

}
