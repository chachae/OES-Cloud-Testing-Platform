package com.oes.server.exam.online.service.impl;

import com.oes.common.core.entity.R;
import com.oes.server.exam.online.client.ScoreClient;
import com.oes.server.exam.online.client.SystemUserClient;
import com.oes.server.exam.online.service.IExamMonitorService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/20 18:31
 */
@Service
@RequiredArgsConstructor
public class ExamMonitorServiceImpl implements IExamMonitorService {

  private final SystemUserClient systemUserClient;
  private final ScoreClient scoreClient;

  @Override
  public Map<String, Object> statisticExamRatio(String deptIds, Long paperId) {
    // 考生总数
    R<Integer> ans1 = systemUserClient.countByDeptIds(deptIds);
    // 到场考生总数
    R<Integer> ans2 = scoreClient.countByPaperId(String.valueOf(paperId));
    Map<String, Object> resultMap = new HashMap<>(2);
    resultMap.put("userCount", ans1.getData());
    resultMap.put("scoreCount", ans2.getData());
    return resultMap;
  }
}
