package com.oes.server.system.service.impl;

import cn.hutool.http.HtmlUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
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
  @DS(DataSourceConstant.SLAVE)
  public AnnounceContent getAnnounceContent(Long contentId) {
    AnnounceContent content = baseMapper.selectOne(
        new LambdaQueryWrapper<AnnounceContent>().eq(AnnounceContent::getContentId, contentId));
    // 解析 Html 转义内容
    content.setHtmlContent(HtmlUtil.unescape(content.getHtmlContent()));
    return content;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public AnnounceContent addAnnounceContent(AnnounceContent announceContent) {
    // Html 转义
    announceContent.setHtmlContent(HtmlUtil.escape(announceContent.getHtmlContent()));
    baseMapper.insert(announceContent);
    return announceContent;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateAnnounceContent(AnnounceContent announceContent) {
    // Html 转义
    announceContent.setHtmlContent(HtmlUtil.escape(announceContent.getHtmlContent()));
    baseMapper.updateById(announceContent);
  }
}