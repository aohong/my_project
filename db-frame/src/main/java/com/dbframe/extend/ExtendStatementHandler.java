package com.dbframe.extend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;

import com.dbframe.executor.GeneratedKey;
import com.dbframe.support.CommonUtils;
import com.dbframe.support.Constants;

/**
 * 
 * 类ExtendStatementHandler.java的实现描述：TODO 类实现描述 
 * @author leyuanren 2013-7-11 上午11:10:30
 */
public class ExtendStatementHandler implements StatementHandler {

    private StatementHandler delegate;
    private Configuration configuration;

    public ExtendStatementHandler(StatementHandler delegate,
            Configuration configuration) {
        this.delegate = delegate;
        this.configuration = configuration;
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        Statement statement = null;
        Object paramObj = delegate.getBoundSql().getParameterObject();
        try {
            if (paramObj instanceof Map) {
                GeneratedKey gk = (GeneratedKey) CommonUtils
                                .getFromMybatisParameter((Map<?, ?>) paramObj,Constants.GENERATE_KEY);
                if (gk != null && gk.getStatment() == null
                        && configuration.isUseGeneratedKeys()) {
                    statement = connection.prepareStatement(delegate
                            .getBoundSql().getSql(),
                            PreparedStatement.RETURN_GENERATED_KEYS);
                    Integer defaultTimeout = configuration
                            .getDefaultStatementTimeout();
                    if (defaultTimeout != null) {
                        statement.setQueryTimeout(defaultTimeout);
                    }
                }
            }
            if (statement == null) {
                statement = delegate.prepare(connection);
            }
            return statement;
        } catch (SQLException e) {
            closeStatement(statement);
            throw e;
        } catch (Exception e) {
            closeStatement(statement);
            throw new ExecutorException(
                    "Error preparing statement.  Cause: " + e, e);
        }
    }

    private void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
        }
    }

    public void parameterize(Statement statement) throws SQLException {
        delegate.parameterize(statement);
    }

    public void batch(Statement statement) throws SQLException {
        delegate.batch(statement);
    }

    public int update(Statement statement) throws SQLException {
        return delegate.update(statement);
    }

    public <E> List<E> query(Statement statement,
            ResultHandler resultHandler) throws SQLException {
        return delegate.<E> query(statement, resultHandler);
    }

    public BoundSql getBoundSql() {
        return delegate.getBoundSql();
    }

    public ParameterHandler getParameterHandler() {
        return delegate.getParameterHandler();
    }


}
