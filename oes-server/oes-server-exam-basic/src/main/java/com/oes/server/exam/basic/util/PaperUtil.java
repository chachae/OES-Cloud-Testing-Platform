package com.oes.server.exam.basic.util;

import com.oes.common.core.entity.exam.Paper;
import com.oes.common.core.entity.exam.PaperQuestion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 试卷操作工具类
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/9 15:49
 */
public class PaperUtil {

  private PaperUtil() {
  }

  /**
   * 为试卷题目类型分组
   *
   * @param paper 试卷信息
   */
  public static void groupQuestions(Paper paper) {
    List<PaperQuestion> questions = paper.getPaperQuestionList();

    // 按题目类型分类
    Collection<List<PaperQuestion>> collection = questions.stream()
        .collect(Collectors.groupingBy(PaperQuestion::getTypeId)).values();

    List<Map<String, Object>> result = new ArrayList<>(collection.size());

    for (List<PaperQuestion> objs : collection) {
      Map<String, Object> questionMap = new HashMap<>(2);
      questionMap.put(Paper.TYPE_ID_KEY, objs.get(0).getTypeId());
      questionMap.put(Paper.QUESTION_KEY, objs);
      result.add(questionMap);
    }
    paper.setPaperQuestions(result);
  }

}
