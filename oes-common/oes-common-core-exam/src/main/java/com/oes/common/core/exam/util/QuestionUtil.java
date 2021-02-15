/*
 *
 * Copyright 2017-2021 chachae@foxmail.com(chenyuexin)
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.oes.common.core.exam.util;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.exam.constant.QuestionTransferConstant;
import com.oes.common.core.exam.entity.QuestionTransfer;
import com.oes.common.core.util.JSONUtil;
import java.util.List;

/**
 * @author chachae
 * @date 2021/2/15
 * @since v1.0
 */
public final class QuestionUtil {

  private QuestionUtil() {
  }

  public static String validateImportQuestion(QuestionTransfer questionTransfer) {
    StringBuilder errorMessage = new StringBuilder();
    Long typeId = questionTransfer.getTypeId();
    if (typeId == null) {
      errorMessage.append(QuestionTransferConstant.TYPE_ERROR + ",");
      return errorMessage.toString().trim();
    }
    // 通用非空检验
    boolean ans = validateIsNotEmpty(questionTransfer, errorMessage);
    if (!ans) {
      return errorMessage.toString().trim();
    }

    //  单选多选校验
    if (typeId == 1L || typeId == 2L) {
      validateChoice(questionTransfer, errorMessage);
    } else if (typeId == 3L) {
      validateJudge(questionTransfer, errorMessage);
    } else if (typeId == 4L) {
      validateFill(questionTransfer, errorMessage);
    }
    return errorMessage.toString().trim();
  }

  private static boolean validateIsNotEmpty(QuestionTransfer questionTransfer, StringBuilder errorMessage) {
    String questionName = questionTransfer.getQuestionName();
    Long courseId = questionTransfer.getCourseId();
    Integer difficult = questionTransfer.getDifficult();
    String rightKey = questionTransfer.getRightKey();
    if (StrUtil.isEmpty(questionName)) {
      errorMessage.append(QuestionTransferConstant.QUESTION_NAME_NOT_EMPTY + ",");
    }
    if (courseId == null) {
      errorMessage.append(QuestionTransferConstant.COURSE_ID_NOT_EMPTY + ",");
    }
    if (difficult == null) {
      errorMessage.append(QuestionTransferConstant.DIFF_NOT_EMPTY + ",");
    }
    if (StrUtil.isEmpty(rightKey)) {
      errorMessage.append(QuestionTransferConstant.RIGHT_KEY_NOT_EMPTY + ",");
    }
    return errorMessage.length() == 0;
  }

  private static void validateChoice(QuestionTransfer questionTransfer, StringBuilder errorMessage) {
    Long typeId = questionTransfer.getTypeId();
    String options = questionTransfer.getOptions();
    String rightKey = questionTransfer.getRightKey();
    if (StrUtil.isEmpty(options)) {
      errorMessage.append(QuestionTransferConstant.OPTIONS_NOT_EMPTY + ",");
      return;
    }

    List<String> optionList = StrUtil.split(options, ',');
    if (typeId == 1L && !optionList.contains(rightKey)) {
      errorMessage.append(QuestionTransferConstant.CHOICE_RIGHT_KEY_MATCH_ERROR + ",");
      return;
    }

    List<String> rightKeyList = StrUtil.split(rightKey, ',');
    if (typeId == 2L) {
      for (String cur : rightKeyList) {
        if (!optionList.contains(cur)) {
          errorMessage.append(QuestionTransferConstant.CHOICE_RIGHT_KEY_MATCH_ERROR + ",");
          return;
        }
      }
      // 多选正确答案转JSON
      questionTransfer.setOptions(JSONUtil.encode(rightKeyList));
    }
  }

  private static void validateJudge(QuestionTransfer questionTransfer, StringBuilder errorMessage) {
    String rightKey = questionTransfer.getRightKey();
    if (rightKey.equals(QuestionTransferConstant.JUDGE_RIGHT_CN)) {
      // 正确
      questionTransfer.setRightKey(QuestionTransferConstant.JUDGE_RIGHT_CODE);
    } else if (rightKey.equals(QuestionTransferConstant.JUDGE_BAD_CN)) {
      // 错误
      questionTransfer.setRightKey(QuestionTransferConstant.JUDGE_BAD_CODE);
    } else {
      // 异常
      errorMessage.append(QuestionTransferConstant.JUDGE_RIGHT_KEY_MATCH_ERROR + ",");
    }
  }

  private static void validateFill(QuestionTransfer questionTransfer, StringBuilder errorMessage) {
    String rightKey = questionTransfer.getRightKey();
    List<String> rightKeyList = StrUtil.split(rightKey, ',');
    // 填空题正确答案转JSON
    questionTransfer.setOptions(JSONUtil.encode(rightKeyList));
  }
}
