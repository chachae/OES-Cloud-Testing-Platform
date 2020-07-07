package com.oes.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.system.AnnounceContent;
import com.oes.server.system.mapper.AnnounceContentMapper;
import com.oes.server.system.service.IAnnounceContentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统公告内容表(AnnounceContent)表服务实现类
 *
 * @author chachae
 * @since 2020-07-06 17:42:38
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AnnounceContentServiceImpl extends
    ServiceImpl<AnnounceContentMapper, AnnounceContent> implements
    IAnnounceContentService {

  @Override
  public AnnounceContent getAnnounceContent(Long contentId) {
    return baseMapper.selectOne(
        new LambdaQueryWrapper<AnnounceContent>().eq(AnnounceContent::getContentId, contentId));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public AnnounceContent addAnnounceContent(AnnounceContent announceContent) {
    baseMapper.insert(announceContent);
    return announceContent;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateAnnounceContent(AnnounceContent announceContent) {
    baseMapper.updateById(announceContent);
  }
}