package com.oes.server.job.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.util.SortUtil;
import com.oes.server.job.entity.JobLog;
import com.oes.server.job.mapper.JobLogMapper;
import com.oes.server.job.service.IJobLogService;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 */
@Slf4j
@Service("JobLogService")
public class JobLogServiceImpl extends
    ServiceImpl<JobLogMapper, JobLog> implements IJobLogService {

  @Override
  public IPage<JobLog> findJobLogs(QueryParam request, JobLog jobLog) {
    LambdaQueryWrapper<JobLog> queryWrapper = new LambdaQueryWrapper<>();

    if (StrUtil.isNotBlank(jobLog.getBeanName())) {
      queryWrapper.eq(JobLog::getBeanName, jobLog.getBeanName());
    }
    if (StrUtil.isNotBlank(jobLog.getMethodName())) {
      queryWrapper
          .eq(JobLog::getMethodName, jobLog.getMethodName());
    }
    if (StrUtil.isNotBlank(jobLog.getStatus())) {
      queryWrapper.eq(JobLog::getStatus, jobLog.getStatus());
    }
    Page<JobLog> page = new Page<>(request.getPageNum(),
        request.getPageSize());
    SortUtil.handlePageSort(request, page, "createTime", SystemConstant.ORDER_DESC, true);
    return this.page(page, queryWrapper);
  }

  @Override
  public void saveJobLog(JobLog log) {
    this.save(log);
  }

  @Override
  public void deleteJobLogs(String[] jobLogIds) {
    List<String> list = Arrays.asList(jobLogIds);
    this.baseMapper.deleteBatchIds(list);
  }

}
