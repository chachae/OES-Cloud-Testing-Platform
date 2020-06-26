package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IScoreService extends IService<Score> {

  /**
   * 分页查询分数数据
   *
   * @param score 模糊条件
   * @return {@link IPage<Score>} 分页结果集
   */
  IPage<Score> pageScore(QueryScoreDto score);

  /**
   * 通过分数信息查询分数
   *
   * @param paperId 试卷编号
   * @param userId  用户编号
   * @return 分数列表
   */
  Score getScore(Long userId, Long paperId);

  /**
   * 删除学期数据
   *
   * @param paperId 试卷编号
   * @param userId  考生编号
   */
  void deleteScore(Long userId, Long paperId);

  /**
   * 更新学期数据
   *
   * @param score 分数信息
   */
  void updateScore(Score score);

  /**
   * 增加学期数据
   *
   * @param score 分数信息
   */
  void createScore(Score score);
}