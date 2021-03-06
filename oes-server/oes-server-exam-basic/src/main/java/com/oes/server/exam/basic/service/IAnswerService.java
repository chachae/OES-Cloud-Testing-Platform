package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import java.util.List;
import java.util.Map;
import org.springframework.scheduling.annotation.Async;

/**
 * @author chachae
 * @since 2020-06-03 16:43:13
 */
public interface IAnswerService extends IService<Answer> {


  /**
   * 分页查询学生答案数据
   *
   * @param answer 模糊条件
   * @return {@link IPage<Answer>} 分页结果集
   */
  IPage<Answer> pageAnswer(QueryAnswerDto answer);

  /**
   * 获取学生答题数据
   *
   * @param userId  用户id
   * @param paperId 试卷编号
   * @return {@link Answer} 信息
   */
  List<Answer> getAnswerList(Long userId, Long paperId);

  /**
   * 获取答题记录问题id和答题信息键值表
   *
   * @param userId  用户名
   * @param paperId 试卷编号
   * @return HashMap
   */
  Map<Long, Answer> getAnswerMap(Long userId, Long paperId);


  /**
   * 获取学生答题数据
   *
   * @param entity 查询信息
   * @return {@link List<Map<String, Object>>} 信息
   */
  List<Map<String, Object>> getWarnAnswerList(QueryAnswerDto entity);

  /**
   * 更新学生答案数据
   *
   * @param answer 学生答案信息
   */
  void updateAnswer(Answer answer);

  /**
   * 创建试卷题目的答案
   */
  @Async(SystemConstant.ASYNC_POOL)
  void createDefaultAnswer(long paperId, List<Long> userIds, List<Long> questionIds);

  /**
   * 通过试卷编号删除回答信息
   *
   * @param paperId 试卷编号
   */
  void deleteByPaperId(Long paperId);
}