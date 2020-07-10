package com.oes.ai.function.face.badiu.remote;

import com.oes.ai.function.face.badiu.remote.fallback.RemoteBaiduFaceMatchServiceFallback;
import com.oes.ai.function.ocr.baidu.entity.AccessTokenInfo;
import com.oes.common.core.constant.SystemConstant;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 人脸对比接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/7/9 21:32
 */
@FeignClient(
    name = "BaiduFaceMatchRemoteService",
    url = "${oes.ai.baidu.face-url}",
    fallbackFactory = RemoteBaiduFaceMatchServiceFallback.class
)
public interface IRemoteBaiduFaceMatchService {

  /**
   * 百度人脸对比接口远程调用
   */
  @PostMapping(consumes = SystemConstant.JSON_HEADER)
  ResponseEntity<Map<String, Object>> faceMatch(
      @RequestParam(AccessTokenInfo.KEY_ACCESS_TOKEN) String accessToken, String data);


}
