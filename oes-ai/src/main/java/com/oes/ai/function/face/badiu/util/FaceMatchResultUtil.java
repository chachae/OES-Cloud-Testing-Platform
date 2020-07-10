package com.oes.ai.function.face.badiu.util;

import com.oes.ai.entity.face.FaceMatchInfo;
import com.oes.ai.function.face.badiu.constant.BaiduFaceMatchConstant;
import com.oes.common.core.exception.ApiException;
import java.util.Map;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/10 09:43
 */
public class FaceMatchResultUtil {

  private FaceMatchResultUtil() {
  }

  public static FaceMatchInfo mapToMatchInfo(Map<String, Object> map) {
    return checkScore(getScore(map));
  }

  @SuppressWarnings("unchecked")
  private static Integer getScore(Map<String, Object> map) {

    Object obj = map.get(BaiduFaceMatchConstant.KEY_RESULT);
    if (obj instanceof Map) {
      Double score = (Double) ((Map<String, Object>) obj).get(BaiduFaceMatchConstant.KEY_SCORE);
      return score.intValue();
    }
    return 0;
  }

  /**
   * 比对分值判定（不能低于80）
   *
   * @param score 返回结果分值
   * @return 响应信息
   */
  private static FaceMatchInfo checkScore(Integer score) {
    if (score < 80) {
      throw new ApiException(String.format("综合分值：%s，活体人脸对比失败，请重试", score));
    }
    return new FaceMatchInfo(score);
  }

}
