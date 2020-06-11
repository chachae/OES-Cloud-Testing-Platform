package com.oes.server.examination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
import com.oes.server.examination.entity.system.Term;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/11 16:18
 */
public interface ITermService extends IService<Term> {


  /**
   * 分页查询学期数据
   *
   * @param param 分页参数
   * @param term  模糊条件
   * @return {@link IPage<Term>} 分页结果集
   */
  IPage<Term> pageTerm(Term term, QueryParam param);

  /**
   * 通过学期名查询学期信息
   *
   * @param termName 学期名称
   * @return {@link Term} 学期对象
   */
  Term getByTermName(String termName);

  /**
   * 删除学期数据
   *
   * @param termIds 学期编号集合
   */
  void deleteTerm(String[] termIds);

  /**
   * 更新学期数据
   *
   * @param term 学期信息
   */
  void updateTerm(Term term);

  /**
   * 增加学期数据
   *
   * @param term 学期信息
   */
  void createTerm(Term term);
}
