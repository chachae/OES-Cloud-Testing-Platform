package com.oes.server.exam.online.service;

import java.util.Map;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/20 18:31
 */
public interface IExamMonitorService {

  /**
   * 统计考试到场人数比率
   *
   * @param deptIds 部门编号
   * @param paperId 试卷编号
   * @return /
   */
  Map<String, Object> statisticExamRatio(String deptIds, Long paperId);

}
