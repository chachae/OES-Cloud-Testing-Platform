package com.oes.server.examination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
import com.oes.server.examination.entity.system.Type;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface ITypeService extends IService<Type> {

  /**
   * 分页查询题目类型
   *
   * @param param 分页参数
   * @param type  模糊条件
   * @return 分页结果集
   */
  IPage<Type> pageType(QueryParam param, Type type);

  /**
   * 增加题型
   *
   * @param type 类型信息
   */
  void createType(Type type);

  /**
   * 删除题目
   *
   * @param typeIds 试题编号集合
   */
  void deleteType(String[] typeIds);

  /**
   * 更新题目
   *
   * @param type 试题信息
   */
  void updateType(Type type);

  /**
   * 通过试题类型名称获取实体类型信息
   *
   * @param typeName 试题类型名称
   * @return Type
   */
  Type getByTypeName(String typeName);
}