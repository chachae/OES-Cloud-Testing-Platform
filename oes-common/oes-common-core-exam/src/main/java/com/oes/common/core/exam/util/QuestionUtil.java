package com.oes.common.core.exam.util;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.common.core.exam.entity.Question;
import java.util.List;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/11/16 20:01
 */
public class QuestionUtil {

  // 填空题填空占位符
  private static final String FILL_PLACEHOLDER = "{{#@#}}";
  private static final String FILL_REPLACE = "____";
  private static final long FILL_ID = 4L;


  private QuestionUtil() {
  }

  /**
   * 处理题目集合中的填空题填空占位符
   *
   * @param questions 试题集合
   */
  public static void handleQuestionFillPlaceHolder(List<Question> questions) {
    if (questions != null && !questions.isEmpty()) {
      for (Question question : questions) {
        if (question.getTypeId() == FILL_ID) {
          question.setQuestionName(StrUtil.replace(question.getQuestionName(), FILL_PLACEHOLDER, FILL_REPLACE));
        }
      }
    }
  }

  public static void handlePaperQuestionFillPlaceHolder(List<PaperQuestion> questions) {
    if (questions != null && !questions.isEmpty()) {
      for (PaperQuestion question : questions) {
        if (question.getTypeId() == FILL_ID) {
          question.setQuestionName(StrUtil.replace(question.getQuestionName(), FILL_PLACEHOLDER, FILL_REPLACE));
        }
      }
    }
  }
}
