package com.oes.server.exam.basic.controller;

import com.oes.common.core.entity.R;
import com.oes.common.core.exam.constant.ExamBasicConstant;
import com.oes.common.redis.starter.service.RedisService;
import com.oes.server.exam.basic.service.IAnswerService;
import com.oes.server.exam.basic.service.IPaperService;
import com.oes.server.exam.basic.service.IQuestionService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 大数据统计
 *
 * @author chachae
 * @version v1.0
 * @date 2020/7/9 15:31
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("statistic")
public class StatisticController {

  private final RedisService redisService;
  private final IPaperService paperService;
  private final IAnswerService answerService;
  private final IQuestionService questionService;

  @GetMapping("index")
  public R<Object> examBasicBdStatistic() {
    Map<Object, Object> res = redisService.hmget(ExamBasicConstant.STATISTIC_PREFIX);
    if (!res.isEmpty()) {
      return R.ok(res);
    }

    Map<String, Object> result = new HashMap<>();
    //对应科目试卷排行前10的题目数据
    result.put("topTenPaper", paperService.getTopTenPaperData());
    //统计科目试题排行前10的题目数据
    result.put("topTenQuestion", questionService.getTopTenQuestionData());
    //统计各类型题目的题目数量分布情况
    result.put("typeCountDistribute", questionService.getTypeCountDistribute());
    // 试卷总数
    result.put("totalPaper", paperService.count());
    // 试题总数
    result.put("totalQuestion", questionService.count());
    // 答题总数
    result.put("totalAnswer", answerService.count());

    redisService.hmset(ExamBasicConstant.STATISTIC_PREFIX, result, ExamBasicConstant.STATISTIC_EXPIRE);

    return R.ok(result);
  }

}
