package com.oes.server.exam.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oes.common.core.exam.entity.ExamViolateLog;
import com.oes.common.core.exam.entity.query.QueryExamViolateLogDto;
import com.oes.common.exam.datasource.starter.annotation.ExamInfoScope;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 考试违规行为日志表数据库访问层
 *
 * @author chachae
 * @since 2020-07-15 20:18:29
 */
@ExamInfoScope(methods = {"pageExamViolateLog"}, field = "paper_id")
public interface ExamViolateLogMapper extends BaseMapper<ExamViolateLog> {

  /**
   * 分页搜索违规行为日志
   *
   * @param page   分页数据
   * @param entity 查询条件
   * @return {@link IPage<ExamViolateLog>} 分页结果集
   */
  IPage<ExamViolateLog> pageExamViolateLog(Page<ExamViolateLog> page,
      @Param("violate") QueryExamViolateLogDto entity);

  /**
   * 查询某场考试的所有违规记录
   *
   * @param paperId 试卷编号
   * @return 违规记录集合
   */
  List<ExamViolateLog> selectByPaperId(Long paperId);
}