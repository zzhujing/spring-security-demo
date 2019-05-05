package com.hujing.springsecuritycore.authorize;

import com.hujing.springsecuritycore.constans.SecurityConstants;
import com.hujing.springsecuritycore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author hj
 * @create 2019-05-05 23:50
 */
@Component
public class DefaultAuthorizationConfigProvider implements AuthorizationConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                SecurityConstants.LOGIN_PAGE_URL,
                SecurityConstants.VALIDATE_CODE_URL_,
                SecurityConstants.MOBILE_SIGNIN_URL,
                SecurityConstants.USER_REGISTER_URL,
                securityProperties.getBrowser().getLoginPage(),
                securityProperties.getSocial().getSignUpUrl()
        ).permitAll();
    }
}
