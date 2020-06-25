package com.oes.server.job.controller;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.util.PageUtil;
import com.oes.server.job.entity.JobLog;
import com.oes.server.job.service.IJobLogService;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 */
@Slf4j
@Validated
@RestController
@RequestMapping("log")
@RequiredArgsConstructor
public class JobLogController {

  private final IJobLogService jobLogService;

  @GetMapping
  @PreAuthorize("hasAuthority('job:log:view')")
  public R<Map<String, Object>> jobLogList(QueryParam request, JobLog log) {
    Map<String, Object> dataTable = PageUtil.toPage(this.jobLogService.findJobLogs(request, log));
    return R.ok(dataTable);
  }

  @DeleteMapping("{jobIds}")
  @PreAuthorize("hasAuthority('job:log:delete')")
  public void deleteJobLog(@NotBlank(message = "{required}") @PathVariable String jobIds) {
    String[] ids = jobIds.split(StrUtil.COMMA);
    this.jobLogService.deleteJobLogs(ids);
  }

}
