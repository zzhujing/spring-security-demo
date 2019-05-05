package com.hujing.springsecuritybrowser;

import com.hujing.springsecuritybrowser.authentication.CustomAuthenticationFailureHandler;
import com.hujing.springsecuritybrowser.authentication.CustomAuthenticationSuccessHandler;
import com.hujing.springsecuritybrowser.authentication.CustomSessionExpiredStrategy;
import com.hujing.springsecuritycore.authorize.AuthorizationConfigManager;
import com.hujing.springsecuritycore.code.ValidateCodeAuthenticationFilter;
import com.hujing.springsecuritycore.code.sms.authentication.SmsAuthenticationSecurityConfig;
import com.hujing.springsecuritycore.constans.SecurityConstants;
import com.hujing.springsecuritycore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author hj
 * @create 2019-05-01 15:41
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    private ValidateCodeAuthenticationFilter validateCodeAuthenticationFilter;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsServiceImpl;
    @Autowired
    private SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;
    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;
    @Autowired
    private CustomSessionExpiredStrategy customSessionExpiredStrategy;
    @Autowired
    private AuthorizationConfigManager authorizationConfigManager;

    /**
     * 配置remember-me
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
//        repository.setCreateTableOnStartup(true); //开机启动生成表结构
        repository.setDataSource(dataSource);
        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(validateCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() //开启表单登录
                .loginPage(SecurityConstants.LOGIN_PAGE_URL)
                .loginProcessingUrl(SecurityConstants.LOGIN_PROCESSING_URL)
                .failureHandler(customAuthenticationFailureHandler)
                .successHandler(customAuthenticationSuccessHandler)
                .and()
                .sessionManagement()
                .invalidSessionUrl(SecurityConstants.INVALID_SESSION_URL) //没有session跳转url
                .maximumSessions(1)//最大session存在数
                .expiredSessionStrategy(customSessionExpiredStrategy)
//                .maxSessionsPreventsLogin(true) //设置智能单台登录
                .and()
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeExpire())
                .userDetailsService(userDetailsServiceImpl)
                .and().csrf().disable()
                .apply(smsAuthenticationSecurityConfig).and()
                .apply(springSocialConfigurer);
        authorizationConfigManager.config(http.authorizeRequests());
    }
}
