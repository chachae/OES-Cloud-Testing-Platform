package com.oes.server.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.examination.Score;
import java.util.List;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IScoreService extends IService<Score> {

  /**
   * 通过试卷编号查询成绩集合
   *
   * @param paperIds 试卷编号数组
   * @return {@link List<Score>} 考试成绩集合
   */
  List<Score> getList(String[] paperIds);

}