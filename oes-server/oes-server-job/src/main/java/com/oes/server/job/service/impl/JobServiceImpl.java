package com.oes.server.job.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.util.SortUtil;
import com.oes.server.job.entity.Job;
import com.oes.server.job.helper.ScheduleUtils;
import com.oes.server.job.mapper.JobMapper;
import com.oes.server.job.service.IJobService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 */
@Slf4j
@Service("JobService")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

  private final Scheduler scheduler;

  /**
   * 项目启动时，初始化定时器
   */
  @PostConstruct
  public void init() {
    List<Job> scheduleJobList = this.baseMapper.queryList();
    // 如果不存在，则创建
    scheduleJobList.forEach(scheduleJob -> {
      CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
      if (cronTrigger == null) {
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
      } else {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
      }
    });
  }

  @Override
  public Job findJob(Long jobId) {
    return this.getById(jobId);
  }

  @Override
  public IPage<Job> findJobs(QueryParam request, Job job) {
    LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();

    if (StrUtil.isNotBlank(job.getBeanName())) {
      queryWrapper.eq(Job::getBeanName, job.getBeanName());
    }
    if (StrUtil.isNotBlank(job.getMethodName())) {
      queryWrapper.eq(Job::getMethodName, job.getMethodName());
    }
    if (StrUtil.isNotBlank(job.getParams())) {
      queryWrapper.like(Job::getParams, job.getParams());
    }
    if (StrUtil.isNotBlank(job.getRemark())) {
      queryWrapper.like(Job::getRemark, job.getRemark());
    }
    if (StrUtil.isNotBlank(job.getStatus())) {
      queryWrapper.eq(Job::getStatus, job.getStatus());
    }

    if (StrUtil.isNotBlank(job.getCreateTimeFrom()) && StrUtil.isNotBlank(job.getCreateTimeTo())) {
      queryWrapper
          .ge(Job::getCreateTime, job.getCreateTimeFrom())
          .le(Job::getCreateTime, job.getCreateTimeTo());
    }
    Page<Job> page = new Page<>(request.getPageNum(), request.getPageSize());
    SortUtil.handlePageSort(request, page, "createTime", SystemConstant.ORDER_DESC, true);
    return this.page(page, queryWrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createJob(Job job) {
    job.setCreateTime(new Date());
    job.setStatus(Job.ScheduleStatus.PAUSE.getValue());
    this.save(job);
    ScheduleUtils.createScheduleJob(scheduler, job);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateJob(Job job) {
    ScheduleUtils.updateScheduleJob(scheduler, job);
    this.baseMapper.updateById(job);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteJobs(String[] jobIds) {
    List<String> list = Arrays.asList(jobIds);
    list.forEach(jobId -> ScheduleUtils.deleteScheduleJob(scheduler, Long.valueOf(jobId)));
    this.baseMapper.deleteBatchIds(list);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateBatch(String jobIds, String status) {
    List<String> list = Arrays.asList(jobIds.split(StrUtil.COMMA));
    Job job = new Job();
    job.setStatus(status);
    this.baseMapper.update(job, new LambdaQueryWrapper<Job>().in(Job::getJobId, list));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void run(String jobIds) {
    String[] list = jobIds.split(StrUtil.COMMA);
    Arrays.stream(list)
        .forEach(jobId -> ScheduleUtils.run(scheduler, this.findJob(Long.valueOf(jobId))));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void pause(String jobIds) {
    String[] list = jobIds.split(StrUtil.COMMA);
    Arrays.stream(list).forEach(jobId -> ScheduleUtils.pauseJob(scheduler, Long.valueOf(jobId)));
    this.updateBatch(jobIds, Job.ScheduleStatus.PAUSE.getValue());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void resume(String jobIds) {
    String[] list = jobIds.split(StrUtil.COMMA);
    Arrays.stream(list).forEach(jobId -> ScheduleUtils.resumeJob(scheduler, Long.valueOf(jobId)));
    this.updateBatch(jobIds, Job.ScheduleStatus.NORMAL.getValue());
  }
}
