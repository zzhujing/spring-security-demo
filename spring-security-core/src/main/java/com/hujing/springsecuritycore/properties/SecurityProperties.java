package com.hujing.springsecuritycore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hj
 * @create 2019-05-01 18:37
 */
@ConfigurationProperties(prefix = "security.properties")
@Data
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();
    private ValidateCodeProperties code = new ValidateCodeProperties();
    private SocialProperties social = new SocialProperties();
    private OAuth2Properties oauth2 = new OAuth2Properties();
}
