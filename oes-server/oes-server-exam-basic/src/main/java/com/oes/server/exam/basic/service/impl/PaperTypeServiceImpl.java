package com.oes.server.exam.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.exam.PaperType;
import com.oes.server.exam.basic.mapper.PaperTypeMapper;
import com.oes.server.exam.basic.service.IPaperTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperTypeServiceImpl extends ServiceImpl<PaperTypeMapper, PaperType> implements
    IPaperTypeService {

  @Override
  public Boolean checkScore(PaperType paperType) {
    if (paperType.getScore() == null) {
      return false;
    }
    PaperType res = getPaperType(paperType.getPaperId(), paperType.getTypeId());
    return res != null && res.getScore() >= paperType.getScore();
  }

  @Override
  public PaperType getPaperType(Long paperId, Long typeId) {
    return baseMapper.selectOne(new LambdaQueryWrapper<PaperType>()
        .eq(PaperType::getPaperId, paperId)
        .eq(PaperType::getTypeId, typeId));
  }
}