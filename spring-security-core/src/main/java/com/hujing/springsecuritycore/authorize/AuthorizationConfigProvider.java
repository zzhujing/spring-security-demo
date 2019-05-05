package com.hujing.springsecuritycore.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author hj
 * @create 2019-05-05 23:46
    授权配置父接口，为适应多了模块的授权配置
 */
public interface AuthorizationConfigProvider {

    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
