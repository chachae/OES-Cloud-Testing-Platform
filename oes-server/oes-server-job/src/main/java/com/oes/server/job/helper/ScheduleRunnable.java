package com.oes.server.job.helper;

import com.oes.server.job.util.SpringContextUtil;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

/**
 * 执行定时任务
 *
 * @author chachae
 */
@Slf4j
public class ScheduleRunnable implements Runnable {

  private final Object target;
  private final Method method;
  private final String params;

  ScheduleRunnable(String beanName, String methodName, String params)
      throws NoSuchMethodException, SecurityException {
    this.target = SpringContextUtil.getBean(beanName);
    this.params = params;

    if (StringUtils.isNotBlank(params)) {
      this.method = target.getClass().getDeclaredMethod(methodName, String.class);
    } else {
      this.method = target.getClass().getDeclaredMethod(methodName);
    }
  }

  @Override
  public void run() {
    try {
      ReflectionUtils.makeAccessible(method);
      if (StringUtils.isNotBlank(params)) {
        method.invoke(target, params);
      } else {
        method.invoke(target);
      }
    } catch (Exception e) {
      log.error("执行定时任务失败", e);
    }
  }

}
