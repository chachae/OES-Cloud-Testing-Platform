package com.oes.server.exam.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.PaperDept;
import com.oes.server.exam.basic.mapper.PaperDeptMapper;
import com.oes.server.exam.basic.service.IPaperDeptService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperDeptServiceImpl extends ServiceImpl<PaperDeptMapper, PaperDept> implements IPaperDeptService {

  @Override
  // 不需要加事务，被上层试卷「PaperService#deletePaper」删除接口调用，重复事务会导致事务失效
  // @Transactional(rollbackFor = Exception.class)
  public void deleteBatchByPaperIds(String[] paperIds) {
    baseMapper.delete(new LambdaQueryWrapper<PaperDept>().in(PaperDept::getPaperId, Arrays.asList(paperIds)));
  }

  @Override
  public void deleteByPaperId(Long paperId) {
    baseMapper.delete(new LambdaQueryWrapper<PaperDept>().eq(PaperDept::getPaperId, paperId));
  }

  @Override
  public List<Long> getDeptIdListByPaperId(Long paperId) {
    List<PaperDept> paperDepts = baseMapper.selectList(new LambdaQueryWrapper<PaperDept>().eq(PaperDept::getPaperId, paperId));
    return paperDepts.stream().map(PaperDept::getDeptId).collect(Collectors.toList());
  }

  @Override
  public Integer countByPaperId(String[] paperIds) {
    if (paperIds.length == 1) {
      return baseMapper.selectCount(new LambdaQueryWrapper<PaperDept>().eq(PaperDept::getPaperId, paperIds[0]));
    } else {
      return baseMapper.selectCount(new LambdaQueryWrapper<PaperDept>().in(PaperDept::getPaperId, Arrays.asList(paperIds)));
    }
  }
}