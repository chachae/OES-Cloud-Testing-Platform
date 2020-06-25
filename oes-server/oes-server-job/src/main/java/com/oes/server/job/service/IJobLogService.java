package com.oes.server.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.QueryParam;
import com.oes.server.job.entity.JobLog;
import org.springframework.scheduling.annotation.Async;

/**
 * @author chachae
 */
public interface IJobLogService extends IService<JobLog> {

  /**
   * 获取定时任务日志分页数据
   *
   * @param request request
   * @param jobLog  jobLog
   * @return 定时任务日志分页数据
   */
  IPage<JobLog> findJobLogs(QueryParam request, JobLog jobLog);

  /**
   * 保存定时任务日志
   *
   * @param log 定时任务日志
   */
  @Async(SystemConstant.ASYNC_POOL)
  void saveJobLog(JobLog log);

  /**
   * 删除定时任务日志
   *
   * @param jobLogIds 定时任务日志id数组
   */
  void deleteJobLogs(String[] jobLogIds);
}
