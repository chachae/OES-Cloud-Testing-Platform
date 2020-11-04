package com.oes.ai.function.ocr.aliyun.service.impl;

import com.oes.ai.entity.ocr.IdCardInfo;
import com.oes.ai.entity.ocr.QueryOcrEntity;
import com.oes.ai.function.ocr.aliyun.client.AliyunOcrClient;
import com.oes.ai.function.ocr.aliyun.constant.AliyunOcrConstant;
import com.oes.ai.function.ocr.aliyun.service.IAliyunOcrIdCardService;
import com.oes.common.core.util.JSONUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 13:27
 */
@Service("aliyunOcrIdCardServiceImpl")
@RequiredArgsConstructor
public class AliyunOcrIdCardServiceImpl implements IAliyunOcrIdCardService {

  private final AliyunOcrClient remoteAliyunOcrService;

  @Override
  public IdCardInfo ocrIdCard(QueryOcrEntity aliyunOcr) {
    Map<String, Object> resObj = new HashMap<>(1);

    //configure配置
    Map<String, Object> configObj = new HashMap<>(1);
    configObj.put(AliyunOcrConstant.KEY_OF_SIDE, aliyunOcr.getType());

    // 拼装请求body的json字符串
    resObj.put(AliyunOcrConstant.KEY_OF_IMAGE, aliyunOcr.getImage());
    resObj.put(AliyunOcrConstant.KEY_OF_CONFIG, configObj);

    String result = remoteAliyunOcrService.idCardOcr(resObj.toString());
    return JSONUtil.decodeValue(result, IdCardInfo.class);
  }
}
