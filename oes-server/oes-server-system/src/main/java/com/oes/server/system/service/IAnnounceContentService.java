package com.oes.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.system.AnnounceContent;

/**
 * 系统公告内容表(AnnounceContent)表服务接口
 *
 * @author chachae
 * @since 2020-07-06 17:42:37
 */
public interface IAnnounceContentService extends IService<AnnounceContent> {

  /**
   * 获取对应的公告内容
   *
   * @param contentId 公告编号
   * @return 公告信息
   */
  AnnounceContent getAnnounceContent(Long contentId);

  /**
   * 新增公告内容
   *
   * @param announceContent 公告内筒
   * @return 工信息内容信息
   */
  AnnounceContent addAnnounceContent(AnnounceContent announceContent);

  /**
   * 更新系统公告内容
   *
   * @param announceContent 公告内容
   */
  void updateAnnounceContent(AnnounceContent announceContent);

}