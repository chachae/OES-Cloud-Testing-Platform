package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.exam.Paper;
import com.oes.common.core.entity.exam.PaperType;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IPaperService extends IService<Paper> {

  /**
   * 分页查询试卷信息
   *
   * @param param 分页条件
   * @param paper 模糊搜索条件
   * @return {@link IPage<Paper>} 分页结果集
   */
  IPage<Paper> pagePaper(Paper paper, QueryParam param);

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