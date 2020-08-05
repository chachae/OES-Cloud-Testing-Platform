package com.oes.server.exam.online.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.query.QueryPaperDto;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 18:04
 */
public interface IPaperService extends IService<Paper> {

  /**
   * 获取未开始的正式开始
   *
   * @param entity 查询对象
   * @return {@link IPage<Paper>} 试卷分页对象
   */
  IPage<Paper> getNormalPaper(QueryPaperDto entity);

  /**
   * 获取未开始的模拟开始
   *
   * @param entity 查询对象
   * @return {@link IPage<Paper>} 试卷分页对象
   */
  IPage<Paper> getImitatePaper(QueryPaperDto entity);

  /**
   * 获取某个同学某张试卷的信息
   *
   * @param username 用户名
   * @param paperId  试卷编号
   * @return {@link Paper} 试卷信息集合
   */
  Paper getByPaperIdAndUsername(Long paperId, String username);
}
