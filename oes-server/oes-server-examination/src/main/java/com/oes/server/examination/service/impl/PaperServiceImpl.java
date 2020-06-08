package com.oes.server.examination.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.QueryParam;
import com.oes.server.examination.entity.system.Paper;
import com.oes.server.examination.mapper.PaperMapper;
import com.oes.server.examination.service.IPaperService;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {

  @Override
  public IPage<Paper> pagePaper(Paper paper, QueryParam param) {
    Page<Paper> page = new Page<>(param.getPageNum(), param.getPageSize());
    return baseMapper.pagePaper(page, paper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updatePaper(Paper paper) {
    paper.setUpdateTime(new Date());
    baseMapper.updateById(paper);
  }
}