package com.oes.common.exam.datasource.starter.common;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.oes.common.exam.datasource.starter.annotation.ExamInfoScope;
import java.util.Properties;
import javax.validation.constraints.NotNull;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * 数据权限拦截器
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/29 16:39
 */
public abstract class AbstractExamInfoScopeHandler extends AbstractSqlParserHandler implements
    Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
    MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
    this.sqlParser(metaObject);
    MappedStatement mappedStatement = (MappedStatement) metaObject
        .getValue("delegate.mappedStatement");

    BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
    // 数据权限只针对查询语句
    if (SqlCommandType.SELECT == mappedStatement.getSqlCommandType()) {
      ExamInfoScope examInfoScope = getDataPermission(mappedStatement);
      if (examInfoScope != null && shouldFilter(mappedStatement, examInfoScope)) {
        String originSql = boundSql.getSql();
        String dataPermissionSql = dataPermissionSql(originSql, examInfoScope);
        metaObject.setValue("delegate.boundSql.sql", dataPermissionSql);
      }
    }
    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    if (target instanceof StatementHandler) {
      return Plugin.wrap(target, this);
    }
    return target;
  }

  @Override
  public void setProperties(Properties properties) {
  }

  protected abstract String dataPermissionSql(String originSql, ExamInfoScope examInfoScope);

  private ExamInfoScope getDataPermission(MappedStatement mappedStatement) {
    String mappedStatementId = mappedStatement.getId();
    ExamInfoScope examInfoScope = null;
    try {
      String className = mappedStatementId.substring(0, mappedStatementId.lastIndexOf('.'));
      final Class<?> clazz = Class.forName(className);
      if (clazz.isAnnotationPresent(ExamInfoScope.class)) {
        examInfoScope = clazz.getAnnotation(ExamInfoScope.class);
      }
    } catch (ClassNotFoundException e) {
      return null;
    }
    return examInfoScope;
  }

  private Boolean shouldFilter(@NotNull MappedStatement mappedStatement,
      @NotNull ExamInfoScope examInfoScope) {
    String methodName = StrUtil.subAfter(mappedStatement.getId(), '.', true);
    String methodPrefix = examInfoScope.methodPrefix();
    // 方法名前缀过滤
    if (StrUtil.isNotBlank(methodPrefix) && methodName.startsWith(methodPrefix)) {
      return true;
    }
    // 方法全名过滤
    String[] methods = examInfoScope.methods();
    for (String method : methods) {
      if (method.equals(methodName)) {
        return true;
      }
    }
    return false;
  }
}

