package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.exam.Score;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IScoreService extends IService<Score> {

  /**
   * 分页查询分数数据
   *
   * @param param 分页参数
   * @param score 模糊条件
   * @return {@link IPage<Score>} 分页结果集
   */
  IPage<Score> pageScore(Score score, QueryParam param);

  /**
   * 删除学期数据
   *
   * @param scoreIds 分数编号集合
   */
  void deleteScore(String[] scoreIds);

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