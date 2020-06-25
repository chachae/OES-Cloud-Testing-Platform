package com.oes.server.exam.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oes.common.core.exam.entity.PaperQuestion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface PaperQuestionMapper extends BaseMapper<PaperQuestion> {

  /**
   * 通过试卷编号查询试题集合
   *
   * @param paperId 试卷编号
   * @return {@link List<PaperQuestion>} 试题集合
   */
  List<PaperQuestion> selectListByPaperId(@Param("paperId") Long paperId);

}