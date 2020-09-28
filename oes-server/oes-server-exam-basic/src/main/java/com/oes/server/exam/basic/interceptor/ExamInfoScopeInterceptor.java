package com.oes.server.exam.basic.interceptor;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.auth.CurrentUser;
import com.oes.common.core.util.SecurityUtil;
import com.oes.common.exam.datasource.starter.annotation.ExamInfoScope;
import com.oes.common.exam.datasource.starter.common.AbstractExamInfoScopeHandler;
import com.oes.common.exam.datasource.starter.constant.ScopeFieldConstant;
import com.oes.server.exam.basic.manager.ExamBasicManager;
import com.oes.server.exam.basic.util.SpringUtil;
import java.io.StringReader;
import java.sql.Connection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

/**
 * 在线考试服务数据权限拦截器
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/29 16:39
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class ExamInfoScopeInterceptor extends AbstractExamInfoScopeHandler {

  public String dataPermissionSql(String originSql, ExamInfoScope examInfoScope) {
    try {
      // 字段为空或者用户为空，均返回默认 sql
      CurrentUser user = SecurityUtil.getCurrentUser();
      if (StrUtil.isBlank(examInfoScope.field()) || user == null) {
        return originSql;
      }

      List<String> curRoles = StrUtil.split(user.getRoleId(), StrUtil.C_COMMA);
      if (!examInfoScope.filterAdmin() && curRoles.contains(String.valueOf(SystemConstant.SYS_ADMIN_ROLE_ID))) {
        return originSql;
      }

      // 获取 ExamBasicManager Bean
      ExamBasicManager managerBean = SpringUtil.getBean(ExamBasicManager.class);

      if (managerBean != null) {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(originSql));
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        Table fromItem = (Table) plainSelect.getFromItem();
        String selectTableName =
            fromItem.getAlias() == null ? fromItem.getName() : fromItem.getAlias().getName();
        // 访问权限拦截
        String dataPermissionSql;
        switch (examInfoScope.field()) {
          case ScopeFieldConstant.COURSE_ID:
            dataPermissionSql = formatSql(selectTableName, examInfoScope.field(),
                managerBean.findUserCourse(SecurityUtil.getCurrentUserId()));
            break;
          case ScopeFieldConstant.PAPER_ID:
            dataPermissionSql = formatSql(selectTableName, examInfoScope.field(),
                managerBean.findPaper(SecurityUtil.getCurrentUserId()));
            break;
          case ScopeFieldConstant.CREATOR_ID:
            dataPermissionSql = formatSql(selectTableName, examInfoScope.field(),
                String.valueOf(user.getUserId()));
            break;
          default:
            dataPermissionSql = originSql;
            break;
        }
        if (plainSelect.getWhere() == null) {
          plainSelect.setWhere(CCJSqlParserUtil.parseCondExpression(dataPermissionSql));
        } else {
          plainSelect.setWhere(new AndExpression(plainSelect.getWhere(),
              CCJSqlParserUtil.parseCondExpression(dataPermissionSql)));
        }
        return select.toString();
      } else {
        return originSql;
      }
    } catch (Exception e) {
      log.warn("get data permission sql fail: {}", e.getMessage());
      return originSql;
    }
  }

  private String formatSql(String tableName, String field, String filter) {
    return String.format("%s.%s in (%s)", tableName, field,
        StrUtil.blankToDefault(filter, "'WARNING: WITHOUT PERMISSION!'"));
  }
}

