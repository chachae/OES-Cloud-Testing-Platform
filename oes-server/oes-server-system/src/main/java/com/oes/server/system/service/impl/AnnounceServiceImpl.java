package com.oes.server.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.system.Announce;
import com.oes.common.core.entity.system.query.AnnounceQueryDto;
import com.oes.common.core.util.SortUtil;
import com.oes.server.system.mapper.AnnounceContentMapper;
import com.oes.server.system.mapper.AnnounceMapper;
import com.oes.server.system.service.IAnnounceService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统公告表(Announce)表服务实现类
 *
 * @author chachae
 * @since 2020-07-06 10:40:31
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AnnounceServiceImpl extends ServiceImpl<AnnounceMapper, Announce> implements
    IAnnounceService {

  private final AnnounceContentMapper announceContentMapper;

  @Override
  @DS(DataSourceConstant.SLAVE)
  public IPage<Announce> pageAnnounce(AnnounceQueryDto announce) {
    Page<Announce> page = new Page<>(announce.getPageNum(), announce.getPageSize());
    // 根据时间降序排序
    SortUtil.handlePageSort(announce, page, "create_time", SystemConstant.ORDER_DESC, false);
    return baseMapper.selectPage(page, wrapperFilter(new LambdaQueryWrapper<>(), announce));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addAnnounce(Announce announce) {
    announce.setCreateTime(new Date());
    baseMapper.insert(announce);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateAnnounce(Announce announce) {
    announce.setUpdateTime(new Date());
    baseMapper.updateById(announce);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteAnnounce(List<String> announceIds) {
    List<Announce> announces = baseMapper.selectBatchIds(announceIds);
    List<Long> contentIds = Lists.transform(announces, Announce::getContentId);
    baseMapper.deleteBatchIds(announceIds);
    announceContentMapper.deleteBatchIds(contentIds);
  }

  private LambdaQueryWrapper<Announce> wrapperFilter(LambdaQueryWrapper<Announce> wrapper,
      AnnounceQueryDto announce) {
    if (announce.getStatus() != null) {
      wrapper.eq(Announce::getStatus, announce.getStatus());
    }
    if (StrUtil.isNotBlank(announce.getKey())) {
      wrapper.like(Announce::getTitle, announce.getKey());
    }
    if (StrUtil.isNotBlank(announce.getCreatorName())) {
      wrapper.like(Announce::getCreatorName, announce.getCreatorName());
    }
    return wrapper;
  }
}