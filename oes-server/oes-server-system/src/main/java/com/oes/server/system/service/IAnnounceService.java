package com.oes.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.system.Announce;
import com.oes.common.core.entity.system.query.AnnounceQueryDto;
import java.util.List;

/**
 * 系统公告表(Announce)表服务接口
 *
 * @author chachae
 * @since 2020-07-06 10:40:30
 */
public interface IAnnounceService extends IService<Announce> {

  /**
   * 分页查询公告信息
   *
   * @param announce 模糊搜索条件
   * @return 分页对象
   */
  IPage<Announce> pageAnnounce(AnnounceQueryDto announce);

  /**
   * 新增公告
   *
   * @param announce 公告信息
   */
  void addAnnounce(Announce announce);

  /**
   * 更新公告
   *
   * @param announce 公告基本信息
   */
  void updateAnnounce(Announce announce);

  /**
   * 删除公告
   *
   * @param announceIds 公告编号数组
   */
  void deleteAnnounce(List<String> announceIds);
}