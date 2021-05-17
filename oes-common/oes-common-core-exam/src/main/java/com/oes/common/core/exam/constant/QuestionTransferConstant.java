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
package com.oes.common.core.exam.constant;

/**
 * @author chachae
 * @date 2021/2/15
 * @since v1.0
 */
public interface QuestionTransferConstant {

  String TYPE_ERROR = "题目类型不存在";

  String QUESTION_NAME_NOT_EMPTY = "题目名称不能为空";

  String COURSE_ID_NOT_EMPTY = "所属课程不能为空";

  String DIFF_NOT_EMPTY = "题目难度不能为空";

  String RIGHT_KEY_NOT_EMPTY = "正确答案不能为空";

  String OPTIONS_NOT_EMPTY = "题目选项不能为空";

  String OPTIONS_TOO_LARGE = "题目选项过多，不能超过10个";

  String JUDGE_RIGHT_KEY_MATCH_ERROR = "判断题选项匹配失败";

  String CHOICE_RIGHT_KEY_MATCH_ERROR = "选择题（单选/多选）题选项匹配失败";

  String JUDGE_RIGHT_CN = "正确";

  String JUDGE_BAD_CN = "错误";

  String JUDGE_RIGHT_CODE = "1";

  String JUDGE_BAD_CODE = "0";
}
