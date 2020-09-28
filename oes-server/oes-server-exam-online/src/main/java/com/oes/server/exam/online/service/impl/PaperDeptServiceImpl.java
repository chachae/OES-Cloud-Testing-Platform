package com.oes.server.exam.online.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.common.core.exam.entity.PaperDept;
import com.oes.server.exam.online.mapper.PaperDeptMapper;
import com.oes.server.exam.online.service.IPaperDeptService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@DS(DataSourceConstant.SLAVE)
public class PaperDeptServiceImpl extends ServiceImpl<PaperDeptMapper, PaperDept> implements IPaperDeptService {

  @Override
  public String getDeptIds(Long paperId) {
    List<PaperDept> paperDepts = baseMapper.selectList(new LambdaQueryWrapper<PaperDept>().eq(PaperDept::getPaperId, paperId));
    return paperDepts.stream().map(o -> String.valueOf(o.getDeptId())).collect(Collectors.joining(","));
  }
}