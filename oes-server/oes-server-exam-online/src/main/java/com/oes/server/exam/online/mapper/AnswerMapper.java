package com.oes.server.exam.online.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020-06-03 16:43:13
 */
public interface AnswerMapper extends BaseMapper<Answer> {

  /**
   * 错题集数据
   * <pre>
   *   必须传入试卷编号和学生编号，前端、控制层入口和业务层已做校验
   * </pre>
   *
   * @param answer 查询条件
   * @return 分页结果集
   */
  List<Answer> getWarnAnswer(@Param("answer") QueryAnswerDto answer);

}