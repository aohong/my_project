package com.dbframe.spring;

import com.dbframe.core.SqlTemplate;
import com.dbframe.core.SqlTemplateContext;

/**
 * 
 * 声明接收SqlTemplateContext Spring环境中会自动注入
 * @author leyuanren 2013-7-4 上午10:29:51
 * 
 * @see SqlTemplateContext
 * @see SqlTemplate
 */
public interface SqlTemplateContextAware{

    void setSqlTemplateContext(SqlTemplateContext SqlTemplateContext);
}
