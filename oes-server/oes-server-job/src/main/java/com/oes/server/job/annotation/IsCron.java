package com.oes.server.job.annotation;

import com.oes.server.job.validator.CronValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author chachae
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CronValidator.class)
public @interface IsCron {

  String message();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}