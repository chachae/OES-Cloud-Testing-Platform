package com.oes.server.exam.online.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.exam.Answer;
import com.oes.common.core.entity.exam.PaperQuestion;
import com.oes.common.core.entity.exam.Type;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.online.mapper.AnswerMapper;
import com.oes.server.exam.online.service.IAnswerService;
import com.oes.server.exam.online.service.IPaperQuestionService;
import com.oes.server.exam.online.util.ScoreUtil;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:14
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

  private final IPaperQuestionService paperQuestionService;

  @Override
  public List<Answer> getAnswer(Long studentId, Long paperId) {
    if (studentId == null) {
      studentId = SecurityUtil.getCurrentUser().getUserId();
    }
    LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
    wrapper
        .eq(Answer::getStudentId, studentId)
        .eq(Answer::getPaperId, paperId);
    return baseMapper.selectList(wrapper);
  }

  @Override
  public Long updateAnswer(Answer answer) {
    // 处理多选题
    checkAndHandleMulChoice(answer);
    // 评分
    Map<String, PaperQuestion> pqMap = paperQuestionService.getMapByPaperId(answer.getPaperId());
    ScoreUtil.mark(answer, pqMap.get(String.valueOf(answer.getQuestionId())));
    checkSaveOrUpdate(answer);
    return answer.getAnswerId();
  }

  private void checkSaveOrUpdate(Answer answer) {
    if (answer.getAnswerId() == null) {
      answer.setCreateTime(new Date());
      baseMapper.insert(answer);
    } else {
      answer.setUpdateTime(new Date());
      baseMapper.updateById(answer);
    }
  }

  /**
   * 处理多项选择题的答案顺序
   */
  private void checkAndHandleMulChoice(Answer answer) {
    if (String.valueOf(answer.getTypeId()).equals(Type.DEFAULT_TYPE_ID_ARRAY[1])
        && answer.getAnswerContent() != null) {
      String[] contents = answer.getAnswerContent().split(StrUtil.COMMA);
      Arrays.sort(contents);
      answer.setAnswerContent(String.join(StrUtil.COMMA, contents));
    }
  }

}