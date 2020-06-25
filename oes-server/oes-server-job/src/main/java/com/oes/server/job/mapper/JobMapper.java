package com.oes.server.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oes.server.job.entity.Job;
import java.util.List;

/**
 * @author chachae
 */
public interface JobMapper extends BaseMapper<Job> {

  /**
   * 获取定时任务列表
   *
   * @return 定时任务列表
   */
  List<Job> queryList();
}