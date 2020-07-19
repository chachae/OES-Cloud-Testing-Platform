package com.oes.common.core.exam.constant;

import lombok.AllArgsConstructor;

/**
 * 考试违规行为枚举
 *
 * @author chachae
 * @version v1.0
 * @date 2020/7/19 17:51
 */
@AllArgsConstructor
public enum ExamViolateBehaviourEnum {

  UNKNOWN_BEHAVIOUR(500, "未知违规行为"),

  CHANGE_BROWSER_TAGS(501, "切换浏览器标签"),

  CHANGE_VUE_ROUTER(502, "切换系统路由"),

  EXIST_BROWSER(503, "离开当前浏览器页面"),

  EXIST_CURRENT_EXAM(504, "中途离开考试"),

  CAPTURE_FAIL(505, "抓拍失败，人为关闭摄像头 [webcam] 权限");

  private final Integer code;

  private final String message;

  public static String getMsg(Integer code) {
    for (ExamViolateBehaviourEnum curObj : ExamViolateBehaviourEnum.values()) {
      if (curObj.code.equals(code)) {
        return curObj.message;
      }
    }
    return UNKNOWN_BEHAVIOUR.message;
  }
}
