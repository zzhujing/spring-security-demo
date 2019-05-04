package com.hujing.springsecuritycore.config;

import com.hujing.springsecuritycore.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hj
 * @create 2019-05-01 18:40
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityPropertyConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
