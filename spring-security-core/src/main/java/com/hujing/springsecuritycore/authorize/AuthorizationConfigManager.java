package com.hujing.springsecuritycore.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author hj
 * @create 2019-05-05 23:55
 * 授权配置自定义管理器，集合所有的config授权配置
 */
@Component
public class AuthorizationConfigManager {

    /**
     * 注入所有的AuthorizationConfigProvider
     */
    @Autowired
    private Set<AuthorizationConfigProvider> providerSet;

    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizationConfigProvider provider : providerSet) {
            provider.config(config);
        }
        config.anyRequest().authenticated();
    }

}
