package com.oes.server.exam.online.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.common.core.entity.EchartMap;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.common.core.exam.entity.Type;
import com.oes.common.core.exam.entity.query.QueryAnswerDto;
import com.oes.common.core.exam.util.GroupUtil;
import com.oes.common.core.exam.util.ScoreUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.online.mapper.AnswerMapper;
import com.oes.server.exam.online.service.IAnswerService;
import com.oes.server.exam.online.service.IPaperQuestionService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
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

  private static final Set<Long> STATISTIC_TYPE = new HashSet<>(3);

  static {
    STATISTIC_TYPE.add(1L);
    STATISTIC_TYPE.add(2L);
    STATISTIC_TYPE.add(3L);
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public List<Answer> getAnswer(String username, Long paperId) {
    if (username == null) {
      username = SecurityUtil.getCurrentUsername();
    }
    LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Answer::getUsername, username).eq(Answer::getPaperId, paperId);
    return baseMapper.selectList(wrapper);
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public List<Answer> getAnswer(Long paperId) {
    return baseMapper.selectList(new LambdaQueryWrapper<Answer>().eq(Answer::getPaperId, paperId));
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public List<Map<String, Object>> getWarnAnswer(QueryAnswerDto answer) {
    answer.setWarn(Answer.IS_WARN);
    List<Answer> answers = baseMapper.getWarnAnswer(answer);
    return !answers.isEmpty() ? GroupUtil.groupQuestion(answers) : null;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
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
      answer.setUsername(SecurityUtil.getCurrentUsername());
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
        && StrUtil.isNotBlank(answer.getAnswerContent())) {
      String[] contents = answer.getAnswerContent().split(StrUtil.COMMA);
      Arrays.sort(contents);
      answer.setAnswerContent(String.join(StrUtil.COMMA, contents));
    }
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public List<Map<String, Object>> statisticAnswers(Long paperId) {
    // 预先准备一个返回体
    List<Map<String, Object>> result = new ArrayList<>();
    // 获取试卷题目正确答案
    Map<String, PaperQuestion> rightKeyMap = paperQuestionService.getMapByPaperId(paperId);
    // 学生答案按照题目编号分组
    List<Answer> paperAnswer = getAnswer(paperId);
    Collection<List<Answer>> answersCollection = paperAnswer.stream()
        .collect(Collectors.groupingBy(Answer::getQuestionId)).values();

    // 处理分组后的答案集合
    for (List<Answer> answers : answersCollection) {
      // 获取题目正确答案
      PaperQuestion paperQuestion = rightKeyMap.get(String.valueOf(answers.get(0).getQuestionId()));
      // 只处理单选、多选、判断
      if (STATISTIC_TYPE.contains(paperQuestion.getTypeId())) {
        // 学生答案按照回答或选项分组
        Map<String, Long> collect = answers.stream()
            .collect(Collectors.groupingBy(Answer::getAnswerContent, Collectors.counting()));
        // 用于存储答题情况分布数据的集合
        List<Map<String, Object>> distribute = new ArrayList<>(collect.size());
        // 统计准确率
        long rightRatio = 0L;
        // 循环学生答案的分布情况，将结构调整为适用于 EChart 的数据结构
        for (Entry<String, Long> entry : collect.entrySet()) {
          if (entry.getKey().equals(paperQuestion.getRightKey())) {
            rightRatio = entry.getValue() / answers.size();
          }
          // 放进学生答案分布集合内
          distribute.add(new EchartMap().pubData(entry.getKey(), entry.getValue()));
        }

        // 单道题目的数据体
        Map<String, Object> objectMap = new HashMap<>(3);
        objectMap.put("paperQuestion", paperQuestion);
        objectMap.put("distribute", distribute);
        objectMap.put("rightRatio", rightRatio);
        result.add(objectMap);
      }
    }
    return result;
  }
}