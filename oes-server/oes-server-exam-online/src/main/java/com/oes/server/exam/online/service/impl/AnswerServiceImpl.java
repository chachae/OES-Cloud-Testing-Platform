package com.oes.server.exam.online.service.impl;

import com.oes.common.core.entity.EchartMap;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.common.core.exam.util.ScoreUtil;
import com.oes.server.exam.online.client.AnswerClient;
import com.oes.server.exam.online.service.IAnswerService;
import com.oes.server.exam.online.service.IPaperQuestionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
public class AnswerServiceImpl implements IAnswerService {

  private final AnswerClient answerClient;
  private final IPaperQuestionService paperQuestionService;

  @Override
  public List<Answer> getAnswerList(String username, Long paperId) {
    R<List<Answer>> ans = answerClient.getAnswerList(username, paperId);
    return ans.getData();
  }

  @Override
  public List<Answer> getAnswer(Long paperId) {
    R<List<Answer>> ans = answerClient.getAnswerList(null, paperId);
    return ans.getData();
  }

  @Override
  public List<Map<String, Object>> getWarnAnswerByPaperId(Long paperId) {
    R<List<Map<String, Object>>> ans = answerClient.getWarningAnswer(paperId);
    return ans.getData();
  }

  @Override
  public Long updateAnswer(Answer answer) {
    markAnswer(answer);
    answerClient.update(answer);
    return answer.getAnswerId();
  }

  @Override
  public Long createAnswer(Answer answer) {
    markAnswer(answer);
    answerClient.add(answer);
    return answer.getAnswerId();
  }

  private void markAnswer(Answer answer) {
    Map<Long, PaperQuestion> ansMap = paperQuestionService.getMapByPaperId(answer.getPaperId());
    ScoreUtil.mark(answer, ansMap.get(answer.getQuestionId()));
  }

  @Override
  public List<Map<String, Object>> statisticAnswers(Long paperId) {
    // 预先准备一个返回体
    List<Map<String, Object>> result = new ArrayList<>();
    // 获取试卷题目正确答案
    Map<Long, PaperQuestion> rightKeyMap = paperQuestionService.getMapByPaperId(paperId);
    // 学生答案按照题目编号分组
    List<Answer> paperAnswer = getAnswer(paperId);
    Collection<List<Answer>> answersCollection = paperAnswer.stream().collect(Collectors.groupingBy(Answer::getQuestionId)).values();

    // 处理分组后的答案集合
    for (List<Answer> answers : answersCollection) {
      // 获取题目正确答案
      PaperQuestion paperQuestion = rightKeyMap.get(answers.get(0).getQuestionId());
      // 只处理单选、多选、判断
      if (paperQuestion.getTypeId() >= 3L) {
        // 学生答案按照回答或选项分组
        Map<String, Long> collect = answers.stream().collect(Collectors.groupingBy(Answer::getAnswerContent, Collectors.counting()));
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