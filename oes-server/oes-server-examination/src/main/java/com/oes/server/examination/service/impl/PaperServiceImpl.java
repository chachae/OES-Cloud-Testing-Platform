package com.oes.server.examination.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.QueryParam;
import com.oes.server.examination.entity.system.Paper;
import com.oes.server.examination.entity.system.PaperType;
import com.oes.server.examination.mapper.PaperMapper;
import com.oes.server.examination.service.IPaperDeptService;
import com.oes.server.examination.service.IPaperService;
import com.oes.server.examination.service.IScoreService;
import com.oes.server.examination.util.PaperUtil;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {

  private final IScoreService scoreService;
  private final IPaperDeptService paperDeptService;

  @Override
  public IPage<Paper> pagePaper(Paper paper, QueryParam param) {
    Page<Paper> page = new Page<>(param.getPageNum(), param.getPageSize());
    IPage<Paper> result = baseMapper.pagePaper(page, paper);
    // 对试卷试题类型分类排序
    if (result.getTotal() > 0L) {
      page.getRecords().forEach(PaperUtil::sortQuestions);
    }
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updatePaper(Paper paper) {
    paper.setUpdateTime(new Date());
    baseMapper.updateById(paper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deletePaper(String[] paperIds) {
    if (canDeleted(paperIds)) {
      baseMapper.deleteBatchIds(Arrays.asList(paperIds));
    }
  }

  @Override
  public void randomCreatePaper(Paper paper, List<PaperType> paperTypes) {

  }

  private boolean canDeleted(String[] paperIds) {
    // 已指派班级 / 存在成绩数据：false，其余情况允许删除
    return CollUtil.isEmpty(paperDeptService.getByPaperId(paperIds)) || CollUtil
        .isEmpty(scoreService.getList(paperIds));
  }


}