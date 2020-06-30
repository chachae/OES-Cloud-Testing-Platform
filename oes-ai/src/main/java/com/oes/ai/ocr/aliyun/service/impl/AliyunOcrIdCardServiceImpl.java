package com.oes.ai.ocr.aliyun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.oes.ai.ocr.aliyun.constant.AliyunOcrConstant;
import com.oes.ai.ocr.aliyun.remote.IRemoteAliyunOcrService;
import com.oes.ai.ocr.aliyun.service.IAliyunOcrIdCardService;
import com.oes.ai.ocr.common.entity.BaseOcrEntity;
import com.oes.ai.ocr.common.entity.IdCardInfo;
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

  private final IRemoteAliyunOcrService remoteAliyunOcrService;

  @Override
  public IdCardInfo ocrIdCard(BaseOcrEntity aliyunOcr) {
    JSONObject resObj = new JSONObject(2);

    //configure配置
    JSONObject configObj = new JSONObject(1);
    configObj.put(AliyunOcrConstant.KEY_OF_SIDE, aliyunOcr.getType());

    // 拼装请求body的json字符串
    resObj.put(AliyunOcrConstant.KEY_OF_IMAGE, aliyunOcr.getImage());
    resObj.put(AliyunOcrConstant.KEY_OF_CONFIG, configObj);

    String result = remoteAliyunOcrService.idCardOcr(resObj.toString());
    return JSONObject.parseObject(result, new TypeReference<IdCardInfo>() {
    }.getType());
  }
}
