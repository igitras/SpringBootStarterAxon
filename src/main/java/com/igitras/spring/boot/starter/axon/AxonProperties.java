package com.igitras.spring.boot.starter.axon;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by meidongxu on 5/29/15.
 */
@ConfigurationProperties(
        prefix = "igitras.spring.boot.starter.axon",
        ignoreUnknownFields = false
)
public class AxonProperties {
    
}
