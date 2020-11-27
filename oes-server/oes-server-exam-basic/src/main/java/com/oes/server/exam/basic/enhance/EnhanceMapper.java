package com.oes.server.exam.basic.enhance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/11/27 14:19
 */
public interface EnhanceMapper<T> extends BaseMapper<T> {

  /**
   * MySQL 批量插入
   *
   * @param entityList 实体集合
   * @return 影响行数
   */
  int insertBatchSomeColumn(List<T> entityList);

}
