package com.oes.server.exam.online.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;
import javax.validation.constraints.NotNull;
import org.springframework.scheduling.annotation.Async;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IScoreService extends IService<Score> {

  /**
   * 通过分数信息查询分数
   * <pre>
   *   该接口为分页接口，且不显示状态无效的成绩
   * </pre>
   *
   * @param score 查询数据
   * @return 分数集合
   */
  IPage<Score> getScore(QueryScoreDto score);

  /**
   * 通过分数信息查询分数
   *
   * @param paperId 试卷编号
   * @param userId  用户编号
   * @return 分数
   */
  Score getScore(Long userId, @NotNull Long paperId);

  /**
   * 增加分数数据
   *
   * @param score 分数信息
   */
  void createScore(Score score);

  /**
   * 更新分数
   *
   * @param score 分数信息
   */
  @Async(SystemConstant.ASYNC_POOL)
  void updateScore(Score score);
}