package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.ExamViolateLog;
import com.oes.common.core.exam.entity.query.QueryExamViolateLogDto;
import java.util.List;

/**
 * 考试违规行为日志服务接口
 *
 * @author chachae
 * @since 2020-07-15 20:18:30
 */
public interface IExamViolateLogService extends IService<ExamViolateLog> {

  /**
   * 分页搜索违规行为日志
   *
   * @param entity 查询对象
   * @return {@link IPage<ExamViolateLog>} 分页结果集
   */
  IPage<ExamViolateLog> pageExamViolateLog(QueryExamViolateLogDto entity);

  /**
   * 查询某场考试的所有违规记录
   *
   * @param paperId 试卷编号
   * @return 违规记录集合
   */
  List<ExamViolateLog> selectByPaperId(Long paperId);

  /**
   * 获取某场考试的违规记录数
   *
   * @param paperId  试卷编号
   * @param username 用户名
   * @return 违规记录数量
   */
  Integer getViolateCount(Long paperId, String username);

  /**
   * 创建考试违规行为日志
   *
   * @param examViolateLog 日志内容
   */
  void saveExamViolateLog(ExamViolateLog examViolateLog);

  /**
   * 删除考试违规日志
   *
   * @param violateId 违规记录编号
   */
  void deleteExamViolateLog(List<String> violateId);
}