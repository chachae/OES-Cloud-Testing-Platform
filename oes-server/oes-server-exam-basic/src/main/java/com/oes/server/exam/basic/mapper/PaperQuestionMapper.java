package com.oes.server.exam.basic.mapper;

import com.oes.common.core.enhance.orm.EnhanceMapper;
import com.oes.common.core.exam.entity.PaperQuestion;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface PaperQuestionMapper extends EnhanceMapper<PaperQuestion> {

  /**
   * 通过试卷编号查询试题集合
   *
   * @param paperId 试卷编号
   * @return {@link List<PaperQuestion>} 试题集合
   */
  List<PaperQuestion> selectListByPaperId(@Param("paperId") Long paperId);

  /**
   * 通过试卷编号查询试题集合（移除题目分析和正确答案等信息）
   *
   * @param paperId 试卷编号
   * @return {@link List<PaperQuestion>} 试题集合
   */
  List<PaperQuestion> selectExamQuestionInfoListByPaperId(@Param("paperId") Long paperId);
}