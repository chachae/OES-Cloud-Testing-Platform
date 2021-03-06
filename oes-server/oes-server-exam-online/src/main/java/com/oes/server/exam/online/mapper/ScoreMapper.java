package com.oes.server.exam.online.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface ScoreMapper extends BaseMapper<Score> {

  /**
   * 分页查询考试分数
   *
   * @param page  分页信息
   * @param score 模糊搜索条件
   * @return 分页结果集
   */
  IPage<Score> pageScore(@Param("score") QueryScoreDto score, Page<Score> page);

  /**
   * 查询考试分数
   *
   * @param score 模糊搜索条件
   * @return 分页结果集
   */
  List<Score> getScore(@Param("score") QueryScoreDto score);
}