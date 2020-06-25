package com.oes.server.job.controller;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.util.PageUtil;
import com.oes.server.job.entity.Job;
import com.oes.server.job.service.IJobService;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class JobController {

  private final IJobService jobService;

  @GetMapping
  @PreAuthorize("hasAuthority('job:view')")
  public R<Map<String, Object>> jobList(QueryParam request, Job job) {
    Map<String, Object> dataTable = PageUtil.toPage(this.jobService.findJobs(request, job));
    return R.ok(dataTable);
  }

  @GetMapping("cron/check")
  public boolean checkCron(String cron) {
    try {
      return CronExpression.isValidExpression(cron);
    } catch (Exception e) {
      return false;
    }
  }

  @PostMapping
  @PreAuthorize("hasAuthority('job:add')")
  public void addJob(@Valid Job job) {
    this.jobService.createJob(job);
  }

  @DeleteMapping("{jobIds}")
  @PreAuthorize("hasAuthority('job:delete')")
  public void deleteJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
    String[] ids = jobIds.split(StrUtil.COMMA);
    this.jobService.deleteJobs(ids);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('job:update')")
  public void updateJob(@Valid Job job) {
    this.jobService.updateJob(job);
  }

  @GetMapping("run/{jobIds}")
  @PreAuthorize("hasAuthority('job:run')")
  public void runJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
    this.jobService.run(jobIds);
  }

  @GetMapping("pause/{jobIds}")
  @PreAuthorize("hasAuthority('job:pause')")
  public void pauseJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
    this.jobService.pause(jobIds);
  }

  @GetMapping("resume/{jobIds}")
  @PreAuthorize("hasAuthority('job:resume')")
  public void resumeJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
    this.jobService.resume(jobIds);
  }
}
