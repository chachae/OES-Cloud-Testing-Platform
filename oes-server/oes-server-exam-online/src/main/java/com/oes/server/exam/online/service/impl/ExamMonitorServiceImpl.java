package com.oes.server.exam.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.Score;
import com.oes.server.exam.online.client.SystemUserClient;
import com.oes.server.exam.online.mapper.ScoreMapper;
import com.oes.server.exam.online.service.IExamMonitorService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/20 18:31
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExamMonitorServiceImpl implements IExamMonitorService {

  private final ScoreMapper scoreMapper;
  private final SystemUserClient systemUserClient;

  @Override
  public Map<String, Object> statisticExamRatio(String deptIds, Long paperId) {
    // 考生总数
    R<Integer> res = systemUserClient.countByDeptIds(deptIds);
    // 到场考生总数
    Integer scoreCount = scoreMapper
        .selectCount(new LambdaQueryWrapper<Score>().eq(Score::getPaperId, paperId));
    Map<String, Object> resultMap = new HashMap<>(2);
    resultMap.put("userCount", res.getData());
    resultMap.put("scoreCount", scoreCount);
    return resultMap;
  }
}
