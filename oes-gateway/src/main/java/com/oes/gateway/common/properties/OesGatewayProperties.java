package com.oes.gateway.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author chachae
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:oes-gateway.properties"})
@ConfigurationProperties(prefix = "oes.gateway")
public class OesGatewayProperties {
    /**
     * 禁止外部访问的 URI，多个值的话以逗号分隔
     */
    private String forbidRequestUri;

    private Boolean enableFlow;
}
