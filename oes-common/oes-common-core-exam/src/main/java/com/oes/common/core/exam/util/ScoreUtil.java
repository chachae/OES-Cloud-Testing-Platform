package com.oes.common.core.exam.util;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.oes.common.core.entity.EchartMap;
import com.oes.common.core.exam.entity.Answer;
import com.oes.common.core.exam.entity.PaperQuestion;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.Type;
import com.oes.common.core.exam.entity.vo.StatisticScoreVo;
import com.oes.common.core.util.DateUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import org.apache.commons.text.similarity.JaccardSimilarity;

/**
 * 自动打分工具
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/24 20:40
 */
public class ScoreUtil {

  private ScoreUtil() {
  }

  /**
   * Jaccard 相似系数
   */
  private static final JaccardSimilarity JACCARD = new JaccardSimilarity();

  /**
   * 对外暴露的统一计算得分方法
   *
   * @param answer        answer
   * @param paperQuestion 当前题目的答案信息
   */
  public static void mark(Answer answer, PaperQuestion paperQuestion) {
    // 默认全部错误，具体的评分方法再进行"是否为错题"设置
    answer.setWarn(Answer.IS_WARN);
    if (Type.DEFAULT_TYPE_ID_ARRAY[0].equals(String.valueOf(answer.getTypeId()))) {
      // 单项选择题
      markChoice(answer, paperQuestion);
    } else if (Type.DEFAULT_TYPE_ID_ARRAY[1].equals(String.valueOf(answer.getTypeId()))) {
      // 多项选择题
      markMulChoice(answer, paperQuestion);
    } else if (Type.DEFAULT_TYPE_ID_ARRAY[2].equals(String.valueOf(answer.getTypeId()))
        || Type.DEFAULT_TYPE_ID_ARRAY[3].equals(String.valueOf(answer.getTypeId()))) {
      // 判断题 / 填空题
      markNormalMark(answer, paperQuestion);
    } else if (Type.DEFAULT_TYPE_ID_ARRAY[4].equals(String.valueOf(answer.getTypeId()))) {
      // 主观题
      subjectiveMark(answer, paperQuestion);
    }
  }

  /**
   * 文本相似读比较
   *
   * @param textA 比较样本A
   * @param textB 比较样本B
   * @return 相似系数（0 ~ 1之间）
   */
  public static double calSimilarity(String textA, String textB) {
    return JACCARD.apply(textA, textB);
  }

  /**
   * 单项选择题计算
   *
   * @param answer        answer
   * @param paperQuestion 当前题目的答案信息
   */
  private static void markChoice(Answer answer, PaperQuestion paperQuestion) {
    answer.setStatus(Answer.STATUS_CORRECT);
    // 快速失败
    if (StrUtil.isBlank(answer.getAnswerContent())) {
      answer.setScore(Score.DEFAULT_SCORE);
      return;
    }
    for (String rk : paperQuestion.getRightKey().split(StrUtil.COMMA)) {
      if (rk.equals(answer.getAnswerContent())) {
        answer.setWarn(Answer.IS_RIGHT);
        answer.setScore(paperQuestion.getScore());
        return;
      }
    }
    answer.setScore(Score.DEFAULT_SCORE);
  }

  /**
   * 多项选择题计算
   *
   * @param answer        answer
   * @param paperQuestion 当前题目的答案信息
   */
  private static void markMulChoice(Answer answer, PaperQuestion paperQuestion) {
    answer.setStatus(Answer.STATUS_CORRECT);
    // 快速失败
    if (StrUtil.isBlank(answer.getAnswerContent())) {
      answer.setScore(Score.DEFAULT_SCORE);
      return;
    }

    if (answer.getAnswerContent().equals(paperQuestion.getRightKey())) {
      answer.setWarn(Answer.IS_RIGHT);
      answer.setScore(paperQuestion.getScore());
      return;
    }

    List<String> rightKeys = StrUtil.split(paperQuestion.getRightKey(), StrUtil.C_COMMA);
    String[] contentArray = answer.getAnswerContent().split(StrUtil.COMMA);
    for (String content : contentArray) {
      if (!rightKeys.contains(content)) {
        answer.setScore(0);
        return;
      }
    }
    answer.setScore(paperQuestion.getScore() / 2);
  }

  /**
   * 常规题目计算
   *
   * @param answer        answer
   * @param paperQuestion 当前题目的答案信息
   */
  private static void markNormalMark(Answer answer, PaperQuestion paperQuestion) {
    answer.setStatus(Answer.STATUS_CORRECT);
    // 快速失败
    if (StrUtil.isBlank(answer.getAnswerContent())) {
      answer.setScore(Score.DEFAULT_SCORE);
      return;
    }
    List<String> rightKeys = StrUtil.split(paperQuestion.getRightKey(), StrUtil.C_COMMA);
    if (!rightKeys.contains(answer.getAnswerContent())) {
      answer.setScore(Score.DEFAULT_SCORE);
    } else {
      answer.setWarn(Answer.IS_RIGHT);
      answer.setScore(paperQuestion.getScore());
    }
  }

  /**
   * 相似系数题目计算
   *
   * @param answer        answer
   * @param paperQuestion 当前题目的答案信息
   */
  private static void subjectiveMark(Answer answer, PaperQuestion paperQuestion) {
    // 相似系数判断默认未批改
    answer.setStatus(Answer.STATUS_NOT_CORRECT);
    // 快速失败
    if (StrUtil.isBlank(answer.getAnswerContent())) {
      answer.setScore(Score.DEFAULT_SCORE);
      return;
    }
    // 相似系数
    Double ratio = calSimilarity(answer.getAnswerContent(), paperQuestion.getRightKey());
    double calScore = ratio * paperQuestion.getScore();
    // 文本长度阈值
    if (answer.getAnswerContent().length() < paperQuestion.getRightKey().length() / 1.1) {
      calScore = calScore >= 2.50d ? calScore * 0.70 : calScore * 0.75;
    }
    int endScore = (int) calScore;
    answer.setScore(endScore);
    if (endScore == paperQuestion.getScore()) {
      answer.setWarn(Answer.IS_RIGHT);
    }
  }

  /**
   * 计算考试的耗时
   *
   * @param creteTime 创建时间
   * @return 成绩
   */
  public static String calTimes(Date creteTime) {
    return DateUtil.calTimes(creteTime, new Date());
  }

  /**
   * 计算成绩
   *
   * @param answers answers
   * @return 成绩
   */
  public static Integer calScore(List<Answer> answers) {
    return answers.stream().map(Answer::getScore).mapToInt(s -> s).sum();
  }

  /**
   * 成绩统计
   *
   * @param scores scores
   * @return 平均分
   */
  public static StatisticScoreVo statisticScore(List<Score> scores) {
    return statisticScoreInts(Lists.transform(scores, Score::getStudentScore));
  }

  /**
   * 成绩统计（包含个人成绩排名）
   *
   * @param scores scores
   * @return 平均分
   */
  public static StatisticScoreVo statisticScore(List<Score> scores, Score score,
      Integer userCount) {
    // 获取基本信息
    List<Integer> res = scores.stream().map(Score::getStudentScore).collect(Collectors.toList());
    Collections.reverse(res);
    StatisticScoreVo baseInfo = statisticScoreInts(res);

    baseInfo.setFullCount(userCount);
    baseInfo.setScoreCount(res.size());
    baseInfo.setRank(res.indexOf(score.getStudentScore()) + 1);
    return baseInfo;
  }

  private static StatisticScoreVo statisticScoreInts(List<Integer> res) {
    return new StatisticScoreVo(calMax(res), calMin(res), calAverage(res), calFraction(res));
  }

  /**
   * 最高分
   *
   * @param scores 分数集合
   * @return 最高分
   */
  private static Integer calMax(List<Integer> scores) {
    OptionalInt optionMax = scores.stream().mapToInt(s -> s).max();
    return optionMax.isPresent() ? optionMax.getAsInt() : 0;
  }

  /**
   * 最低分
   *
   * @param scores 分数集合
   * @return 最低分
   */
  private static Integer calMin(List<Integer> scores) {
    OptionalInt optionMin = scores.stream().mapToInt(s -> s).min();
    return optionMin.isPresent() ? optionMin.getAsInt() : 0;
  }

  /**
   * 平均分
   *
   * @param scores 分数集合
   * @return 平均分
   */
  private static Double calAverage(List<Integer> scores) {
    OptionalDouble optionAvg = scores.stream().mapToInt(s -> s).average();
    return optionAvg.isPresent() ? optionAvg.getAsDouble() : 0;
  }

  /**
   * 分数段统计
   *
   * @param scores 分数集合
   */
  private static List<EchartMap> calFraction(List<Integer> scores) {
    List<EchartMap> result = new ArrayList<>(5);

    long lt60 = scores.stream().filter(s -> s < 60).count();
    long bt60To70 = scores.stream().filter(s -> s < 70 && s >= 60).count();
    long bt70To80 = scores.stream().filter(s -> s < 80 && s >= 70).count();
    long bt80To90 = scores.stream().filter(s -> s < 90 && s >= 80).count();
    long mt90 = scores.stream().filter(s -> s >= 90).count();

    result.add(new EchartMap().pubData("低于60分", lt60));
    result.add(new EchartMap().pubData("60分-70分", bt60To70));
    result.add(new EchartMap().pubData("70分-80分", bt70To80));
    result.add(new EchartMap().pubData("80分-90分", bt80To90));
    result.add(new EchartMap().pubData("90分以上", mt90));
    return result;
  }
}
