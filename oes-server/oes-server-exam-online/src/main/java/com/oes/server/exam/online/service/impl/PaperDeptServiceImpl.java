package com.oes.server.exam.online.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.PaperDept;
import com.oes.server.exam.online.mapper.PaperDeptMapper;
import com.oes.server.exam.online.service.IPaperDeptService;
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
public class PaperDeptServiceImpl extends ServiceImpl<PaperDeptMapper, PaperDept> implements
    IPaperDeptService {

  @Override
  public String getPaperId(Long deptId) {
    List<PaperDept> paperDepts = baseMapper
        .selectList(new LambdaQueryWrapper<PaperDept>().eq(PaperDept::getDeptId, deptId));

    return paperDepts.stream().map(pd -> String.valueOf(pd.getPaperId()))
        .collect(Collectors.joining(StrUtil.COMMA));
  }
}