package com.oes.server.exam.online.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oes.common.core.entity.exam.PaperQuestion;
import java.util.List;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/24 22:38
 */
public interface PaperQuestionMapper extends BaseMapper<PaperQuestion> {

  List<PaperQuestion> selectByPaperId(Long paperId);

}
