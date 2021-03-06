package com.oes.server.exam.basic.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.Term;
import com.oes.common.core.exam.entity.query.QueryTermDto;
import com.oes.common.core.exception.ApiException;
import com.oes.server.exam.basic.mapper.TermMapper;
import com.oes.server.exam.basic.service.IPaperService;
import com.oes.server.exam.basic.service.ITermService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/11 16:19
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TermServiceImpl extends ServiceImpl<TermMapper, Term> implements ITermService {

  private final IPaperService paperService;

  @Override
  public IPage<Term> pageTerm(QueryTermDto term) {
    LambdaQueryWrapper<Term> wrapper = new LambdaQueryWrapper<>();
    if (StrUtil.isNotBlank(term.getTermName())) {
      wrapper.like(Term::getTermName, term.getTermName());
    }
    return baseMapper.selectPage(new Page<>(term.getPageNum(), term.getPageSize()), wrapper);
  }

  @Override
  public Term getByTermName(String termName) {
    return baseMapper.selectOne(new LambdaQueryWrapper<Term>().eq(Term::getTermName, termName));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteTerm(String[] termIds) {
    if (hasPaper(termIds)) {
      throw new ApiException("本学期已存在相关试卷，无法删除");
    }
    baseMapper.deleteBatchIds(Arrays.asList(termIds));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateTerm(Term term) {
    baseMapper.updateById(term);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createTerm(Term term) {
    baseMapper.insert(term);
  }

  private boolean hasPaper(String[] termIds) {
    return paperService.countByTermIds(termIds) > 0;
  }
}
