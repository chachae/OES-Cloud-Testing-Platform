package com.oes.server.examination.util;

import com.oes.server.examination.entity.system.Paper;
import com.oes.server.examination.entity.system.PaperQuestion;
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
   * 试题 HashMap 键
   */
  private static final String TYPE_ID_KEY = "typeId";
  private static final String QUESTION_KEY = "list";

  /**
   * 为试卷题目类型排序
   *
   * @param paper 试卷信息
   */
  public static void sortQuestions(Paper paper) {
    List<PaperQuestion> questions = paper.getPaperQuestionList();

    // 按题目类型分类
    Collection<List<PaperQuestion>> collection = questions.stream()
        .collect(Collectors.groupingBy(PaperQuestion::getTypeId)).values();

    List<Map<String, Object>> result = new ArrayList<>(collection.size());

    for (List<PaperQuestion> objs : collection) {
      Map<String, Object> questionMap = new HashMap<>(2);
      questionMap.put(TYPE_ID_KEY, objs.get(0).getTypeId());
      questionMap.put(QUESTION_KEY, objs);
      result.add(questionMap);
    }

    paper.setPaperQuestions(result);
    paper.setPaperQuestionList(null);
  }

}
