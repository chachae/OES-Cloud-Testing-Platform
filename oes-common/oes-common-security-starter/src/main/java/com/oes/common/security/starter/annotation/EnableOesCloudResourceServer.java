package com.oes.common.security.starter.annotation;

import com.oes.common.security.starter.config.OesCloudResourceServerConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author chachae
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(OesCloudResourceServerConfig.class)
public @interface EnableOesCloudResourceServer {

}
