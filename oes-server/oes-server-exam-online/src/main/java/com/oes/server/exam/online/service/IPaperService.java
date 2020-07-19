package com.oes.server.exam.online.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.exam.entity.Paper;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 18:04
 */
public interface IPaperService extends IService<Paper> {

  /**
   * 获取未开始的正式开始
   *
   * @param param 分页参数
   * @param paper 试卷信息
   * @return {@link IPage<Paper>} 试卷分页对象
   */
  IPage<Paper> getNormalPaper(QueryParam param, Paper paper);

  /**
   * 获取未开始的模拟开始
   *
   * @param param 分页参数
   * @param paper 试卷信息
   * @return {@link IPage<Paper>} 试卷分页对象
   */
  IPage<Paper> getImitatePaper(QueryParam param, Paper paper);

  /**
   * 获取某个同学某张试卷的信息
   *
   * @param studentId 学生编号
   * @param paperId   试卷编号
   * @return {@link Paper} 试卷信息集合
   */
  Paper getByPaperIdAndStudentId(Long studentId, Long paperId);
}
