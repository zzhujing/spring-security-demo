package com.hujing.springsecurityapp.config;

import com.hujing.springsecurityapp.authentication.CustomAuthenticationFailureHandler;
import com.hujing.springsecurityapp.authentication.CustomAuthenticationSuccessHandler;
import com.hujing.springsecuritycore.code.ValidateCodeAuthenticationFilter;
import com.hujing.springsecuritycore.code.sms.authentication.SmsAuthenticationSecurityConfig;
import com.hujing.springsecuritycore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author hj
 * @create 2019-05-04 12:48
 */
@EnableResourceServer
@Configuration
public class SecurityResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;
    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;
    @Autowired
    private ValidateCodeAuthenticationFilter validateCodeAuthenticationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(validateCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() //开启表单登录
                .loginPage("/authentication/required")
                .loginProcessingUrl("/authentication/signIn")
                .failureHandler(customAuthenticationFailureHandler)
                .successHandler(customAuthenticationSuccessHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/required", "/code/**", "/authentication/mobile", "/user/register",
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getSocial().getSignUpUrl()).permitAll() //登录请求不需要身份验证
                .anyRequest() //表示任意资源都需要授权
                .authenticated().and().csrf().disable()
                .apply(smsAuthenticationSecurityConfig).and()
                .apply(springSocialConfigurer)
        ;
    }
}
