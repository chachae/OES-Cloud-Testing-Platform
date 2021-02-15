package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.PaperType;
import com.oes.common.core.exam.entity.query.QueryPaperDto;
import java.util.List;
import java.util.Map;

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
   * @param paperId 试卷编号
   * @param userId 用户id
   * @return {@link Paper} 试卷数据
   */
  Paper getPaper(Long paperId, Long userId);

  /**
   * 统计各科目题量排名前10的题目情况
   *
   * <pre>
   *   返回的集合内 Map 中的数据解释
   *   value：当前科目题目的总数
   *   name：当前课程名称
   * </pre>
   *
   * @return {@link List <Map>} 查询数据题
   */
  List<Map<String, Object>> getTopTenPaperData();

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
   * @param paper 组卷数据
   * @param paperType 试卷试题类型分布数据
   */
  void randomCreatePaper(Paper paper, PaperType paperType);

  /**
   * 通过导入模板的方式抽取题目组卷
   *
   * @param paper 组卷数据
   * @param paperType 试卷试题类型分布数据
   */
  void importCreatePaper(Paper paper, PaperType paperType);

  /**
   * 通过课程编号查询题目数量
   *
   * @param termIds 学期编号集合
   * @return 数量
   */
  Integer countByTermIds(String[] termIds);
}
