package com.oes.common.exam.datasource.starter.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author chachae
 * @since 2020/05/11 22：02
 */
@Configuration
public class OesExamDataSourceAutoConfig {

  /**
   * 注册数据权限
   */
//  @Bean
//  @Order(-1)
//  public AbstractExamInfoScopeHandler dataPermissionInterceptor() {
//    return new AbstractExamInfoScopeHandler();
//  }

  /**
   * 注册分页插件
   */
  @Bean
  @Order(-2)
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    // 攻击 sql 阻断
    List<ISqlParser> sqlParserList = new ArrayList<>();
    sqlParserList.add(new BlockAttackSqlParser());
    paginationInterceptor.setSqlParserList(sqlParserList);
    return paginationInterceptor;
  }
}
