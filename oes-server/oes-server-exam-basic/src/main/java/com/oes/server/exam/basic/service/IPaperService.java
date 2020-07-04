package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.PaperType;
import com.oes.common.core.exam.entity.query.QueryPaperDto;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IPaperService extends IService<Paper> {

  /**
   * 分页查询试卷信息
   *
   * @param paper 模糊搜索条件
   * @return {@link IPage<Paper>} 分页结果集
   */
  IPage<Paper> pagePaper(QueryPaperDto paper);

  /**
   * 通过试卷编号和学生编号获取试卷信息
   *
   * @param paperId   试卷编号
   * @param studentId 用户编号
   * @return {@link Paper} 试卷数据
   */
  Paper getPaper(Long paperId, Long studentId);


  /**
   * 更新试卷信息
   *
   * @param paper 试卷信息
   */
  void updatePaper(Paper paper);

  /**
   * 删除试卷
   *
   * @param paperIds 试卷编号集合
   */
  void deletePaper(String[] paperIds);

  /**
   * 随机抽取题目组卷
   *
   * @param paper     组卷数据
   * @param paperType 试卷试题类型分布数据
   */
  void randomCreatePaper(Paper paper, PaperType paperType);
}