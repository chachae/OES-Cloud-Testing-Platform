package com.oes.server.exam.basic.controller;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.constant.ExamBasicConstant;
import com.oes.common.core.exam.entity.ExamViolateLog;
import com.oes.common.core.exam.entity.query.QueryExamViolateLogDto;
import com.oes.common.core.util.PageUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.basic.service.IExamViolateLogService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 考试违规行为日志表控制层
 *
 * @author chachae
 * @since 2020-07-15 20:18:34
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("exam/violate/log")
public class ExamViolateLogController {

  private final IExamViolateLogService examViolateLogService;

  @GetMapping
  public R<Map<String, Object>> paperExamViolateLog(QueryExamViolateLogDto query) {
    return R.ok(PageUtil.toPage(examViolateLogService.pageExamViolateLog(query)));
  }

  @GetMapping("count")
  public Integer getViolateCount(
      @NotBlank(message = "{required}") Long paperId) {
    return examViolateLogService
        .getViolateCount(paperId, SecurityUtil.getCurrentUser().getUserId());
  }

  /**
   * 检查本人某场考试违规行为是否超出阈值，阈值：{@link ExamBasicConstant#MAX_VIOLATE_COUNT}
   * <p>
   * 请求示例：http://domain:port/exam-basic/exam/violate/log/check?paperId=xx
   * </p>
   *
   * @return {@link Boolean} true / false
   */
  @GetMapping("check")
  public boolean checkHasViolateLog(
      @NotBlank(message = "{required}") Long paperId) {
    // 在成绩无效（未提交前 / 人为修改成绩状态）时，单场考试违规记录超过（包含） 3 条无法进入考试
    return examViolateLogService.getViolateCount(paperId, SecurityUtil.getCurrentUser().getUserId())
        >= ExamBasicConstant.MAX_VIOLATE_COUNT;
  }

  @PostMapping
  public void createViolateLog(@Valid ExamViolateLog examViolateLog) {
    examViolateLogService.saveExamViolateLog(examViolateLog);
  }

  @DeleteMapping("{violateIds}")
  public void createViolateLog(@NotBlank(message = "{required}") @PathVariable String violateIds) {
    List<String> ids = StrUtil.split(violateIds, StrUtil.C_COMMA);
    examViolateLogService.deleteExamViolateLog(ids);
  }
}