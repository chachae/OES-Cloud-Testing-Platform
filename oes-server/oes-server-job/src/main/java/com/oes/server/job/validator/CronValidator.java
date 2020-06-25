package com.oes.server.job.validator;

import com.oes.server.job.annotation.IsCron;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.quartz.CronExpression;

/**
 * 校验是否为合法的 Cron表达式
 *
 * @author chachae
 */
public class CronValidator implements ConstraintValidator<IsCron, String> {

  @Override
  public void initialize(IsCron isCron) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    try {
      return CronExpression.isValidExpression(value);
    } catch (Exception e) {
      return false;
    }
  }
}