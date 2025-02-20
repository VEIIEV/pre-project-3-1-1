package org.example.preproject231.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "custom.success-redirect")
public class AdditionalSecurityProperties {

    private String userUrl;

    private String adminUrl;
}
