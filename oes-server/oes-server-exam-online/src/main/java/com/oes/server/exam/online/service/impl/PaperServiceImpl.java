package com.oes.server.exam.online.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.exam.Paper;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.online.mapper.PaperMapper;
import com.oes.server.exam.online.service.IPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 18:04
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {

  @Override
  public IPage<Paper> getNormalPaper(QueryParam param, Paper paper) {
    paper.setType(Paper.TYPE_NORMAL);
    return filterPaper(param, paper);
  }

  @Override
  public IPage<Paper> getImitatePaper(QueryParam param, Paper paper) {
    paper.setType(Paper.TYPE_IMITATE);
    return filterPaper(param, paper);
  }

  private IPage<Paper> filterPaper(QueryParam param, Paper paper) {
    paper.setStatus(Paper.STATUS_OPEN);
    paper.setDeptIds(String.valueOf(SecurityUtil.getCurrentUser().getDeptId()));
    return baseMapper.pagePaper(paper, new Page<>(param.getPageNum(), param.getPageSize()));
  }
}
