package com.hujing.springsecuritytest;

import com.hujing.springsecuritycore.authorize.AuthorizationConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author hj
 * @create 2019-05-06 0:01
 */
@Component
public class TestAuthorizationConfigProvider implements AuthorizationConfigProvider {

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/test/1")
                .hasRole("ADMIN");
    }
}
