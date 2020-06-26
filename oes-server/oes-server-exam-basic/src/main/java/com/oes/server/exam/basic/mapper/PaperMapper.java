package com.oes.server.exam.basic.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.query.QueryPaperDto;
import com.oes.common.datasource.starter.announcation.DataPermission;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@DataPermission(methods = {"pagePaper"}, field = "creator_id")
public interface PaperMapper extends BaseMapper<Paper> {

  /**
   * 分页查询试卷信息
   *
   * @param page  分页条件
   * @param paper 模糊搜索条件
   * @return {@link IPage<Paper>} 分页结果集
   */
  IPage<Paper> pagePaper(Page<Paper> page, @Param("paper") QueryPaperDto paper);

  /**
   * 通过试卷编号获取试卷信息
   *
   * @param paperId 试卷编号
   * @return {@link Paper} 试卷数据
   */
  Paper selectByPaperId(@Param("paperId") Long paperId);

}