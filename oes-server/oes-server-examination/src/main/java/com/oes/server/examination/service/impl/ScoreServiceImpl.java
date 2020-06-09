package com.oes.server.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.server.examination.entity.system.Score;
import com.oes.server.examination.mapper.ScoreMapper;
import com.oes.server.examination.service.IScoreService;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements IScoreService {

  @Override
  public List<Score> getList(String[] paperIds) {
    return baseMapper
        .selectList(new LambdaQueryWrapper<Score>().in(Score::getPaperId, Arrays.asList(paperIds)));
  }
}