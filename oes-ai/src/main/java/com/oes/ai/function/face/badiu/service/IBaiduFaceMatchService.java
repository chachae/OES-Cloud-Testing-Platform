package com.oes.ai.function.face.badiu.service;

import com.oes.ai.entity.face.FaceMatchInfo;
import com.oes.ai.entity.face.QueryFaceMatch;
import java.util.Map;

/**
 * 百度人脸识别接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 16:41
 */
public interface IBaiduFaceMatchService {

  /**
   * 人脸对比接口
   *
   * @return {@link Map} 返回结果
   */
  FaceMatchInfo faceMatch(QueryFaceMatch matchA);

}
