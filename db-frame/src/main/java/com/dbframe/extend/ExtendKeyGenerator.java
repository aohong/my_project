package com.dbframe.extend;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbframe.support.CommonUtils;
import com.dbframe.support.Constants;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;

import com.dbframe.executor.GeneratedKey;
import com.dbframe.executor.MoreGeneratedKeys;

/**
 * 
 * 类ExtendKeyGenerator.java的实现描述：TODO 类实现描述 
 * @author leyuanren 2013-7-11 上午11:09:20
 */
public class ExtendKeyGenerator implements KeyGenerator {

    private SelectKeyGenerator targetKeyGenerator;

    public ExtendKeyGenerator(SelectKeyGenerator target) {
        this.targetKeyGenerator = target;
    }

    @Override
    public void processBefore(Executor executor, MappedStatement ms,
            Statement stmt, Object parameter) {
        // do nothing
    }

    @SuppressWarnings("unchecked")
    @Override
    public void processAfter(Executor executor, MappedStatement ms,
            Statement stmt, Object parameter) {
        GeneratedKey gk = (GeneratedKey) CommonUtils
                .getFromMybatisParameter((Map<?, ?>) parameter, Constants.GENERATE_KEY);
        if (gk != null) {
            if (gk.getStatment() != null) {
                targetKeyGenerator.processAfter(executor, ms, stmt,
                        parameter);
            } else if (ms.getConfiguration().isUseGeneratedKeys()) {
                //支持MoreGeneratedKeys 获取多个key
                if(gk instanceof MoreGeneratedKeys){
                    MoreGeneratedKeys mgk =(MoreGeneratedKeys)gk;
                    List<Object> paramList  =new ArrayList<Object>(mgk.getSize());
                    for(int i=0;i<mgk.getSize();i++){
                        Map<String,GeneratedKey> paramMap = new HashMap<String,GeneratedKey>();
                        paramMap.put(Constants.GENERATE_KEY, new GeneratedKey());
                        paramList.add(paramMap);
                    }
                    new Jdbc3KeyGenerator().processBatch(ms, stmt, paramList);
                    for(Object obj : paramList){
                        GeneratedKey generatedKey = ((Map<String,GeneratedKey>)obj).get(Constants.GENERATE_KEY);
                        mgk.getKeys().add(generatedKey.getKey());
                    }
                }else{
                    new Jdbc3KeyGenerator().processAfter(executor, ms, stmt,
                            parameter);
                }
            }
        }
    }
}
