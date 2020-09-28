package com.oes.server.exam.basic.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.common.core.exam.entity.PaperType;
import com.oes.server.exam.basic.mapper.PaperTypeMapper;
import com.oes.server.exam.basic.service.IPaperTypeService;
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
@DS(DataSourceConstant.SLAVE)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperTypeServiceImpl extends ServiceImpl<PaperTypeMapper, PaperType> implements IPaperTypeService {

  @Override
  public Boolean checkScore(PaperType paperType) {
    if (paperType.getScore() == null) {
      return false;
    }
    PaperType res = getPaperType(paperType.getPaperId(), paperType.getTypeId());
    return res != null && res.getScore() >= paperType.getScore();
  }

  @Override
  public List<PaperType> getList(Long paperId) {
    LambdaQueryWrapper<PaperType> wrapper = new LambdaQueryWrapper<>();
    if (paperId != null) {
      wrapper.eq(PaperType::getPaperId, paperId);
    }
    return baseMapper.selectList(wrapper);
  }

  @Override
  public PaperType getPaperType(Long paperId, Long typeId) {
    return baseMapper.selectOne(new LambdaQueryWrapper<PaperType>()
        .eq(PaperType::getPaperId, paperId)
        .eq(PaperType::getTypeId, typeId));
  }

  @Override
  @DS(DataSourceConstant.MASTER)
  public void deleteBatchByPaperIds(String[] paperIds) {
    if (paperIds.length == 1) {
      baseMapper.deleteById(paperIds[0]);
    } else {
      baseMapper.deleteBatchIds(Arrays.asList(paperIds));
    }
  }


}