package com.oes.server.exam.online.controller;

import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.R;
import com.oes.common.core.util.PageUtil;
import com.oes.server.exam.online.constant.SocketConfigConstant;
import com.oes.server.exam.online.service.IExamOnlineInfoService;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/8/10 17:36
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("monitor")
public class MonitorController {

  private final IExamOnlineInfoService examOnlineInfoService;
  private final RedisTemplate<String, String> stringRedisTemplate;

  /**
   * 监控考试
   *
   * @param paperId 考试编号
   */
  @GetMapping
  public R<Map<String, Object>> monitorExam(@NotBlank(message = "{required}") String paperId, QueryParam queryParam) {
    // 获取考试在线状态
    List<Object> ans = examOnlineInfoService.listByPaperId(paperId);
    return R.ok(PageUtil.toPage(queryParam.getPageNum().intValue(), queryParam.getPageSize().intValue(), ans));
  }

  /**
   * 监控考试
   *
   * @param message 考试编号
   */
  @PostMapping("send")
  public void postMessage(@NotBlank(message = "{required}") String message) {
    stringRedisTemplate.convertAndSend(SocketConfigConstant.MESSAGE_TOPIC, message);
  }
}
