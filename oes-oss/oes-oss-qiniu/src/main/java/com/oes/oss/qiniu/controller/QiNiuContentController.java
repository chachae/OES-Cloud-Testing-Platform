package com.oes.oss.qiniu.controller;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.entity.R;
import com.oes.common.core.util.PageUtil;
import com.oes.oss.qiniu.entity.QiNiuContent;
import com.oes.oss.qiniu.entity.query.QiNiuQueryDto;
import com.oes.oss.qiniu.service.IQiNiuConfigService;
import com.oes.oss.qiniu.service.IQiNiuContentService;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 七牛云图片处理控制层
 * <p>
 * 远端调用示例：http://domian:port/oss-qiniu/content | http://OES-OSS-Qiniu/oss-qiniu/content
 * </p>
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/27 13:12
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("content")
public class QiNiuContentController {

  private final IQiNiuConfigService qiNiuConfigService;
  private final IQiNiuContentService qiNiuContentService;

  @GetMapping
  public R<Map<String, Object>> page(QiNiuQueryDto query) {
    return R.ok(PageUtil.toPage(qiNiuContentService.pageQiNiuContent(query)));
  }

  @PostMapping
  public R<QiNiuContent> upload(@RequestParam MultipartFile file) {
    QiNiuContent content = qiNiuContentService.upload(file, qiNiuConfigService.getConfig());
    return R.ok(content);
  }

  @PostMapping(value = "/synchronize")
  public R<Object> synchronize() {
    qiNiuContentService.synchronize(qiNiuConfigService.getConfig());
    return R.ok();
  }

  @GetMapping(value = "/download/{id}")
  public R<String> download(@PathVariable Long id) {
    return R.ok(qiNiuContentService
        .download(qiNiuContentService.getById(id), qiNiuConfigService.getConfig()));
  }

  @DeleteMapping("{ids}")
  public void delete(@PathVariable @NotBlank(message = "{required}") String ids) {
    String[] idArray = ids.split(StrUtil.COMMA);
    qiNiuContentService.delete(idArray, qiNiuConfigService.getConfig());
  }
}
