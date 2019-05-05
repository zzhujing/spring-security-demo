package com.hujing.springsecuritycore.social.config;

import com.hujing.springsecuritycore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author hj
 * @create 2019-05-03 12:04
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired(required = false)
    private ConnectionSignUp customConnectionSignUp;
    /**
     * @param connectionFactoryLocator 判定使用什么类型的ConnectionFactory
     * @return 持久化第三方服务商的id，以及本系统的用户id之间的对应关系操作类 。 其中有建表sql在classpath下
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        //设置在没有账号的情况下使用默认的账号规则生成账号
        repository.setConnectionSignUp(customConnectionSignUp);
        return repository;
    }
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }
    //将配置添加到spring security filter chain
    @Bean
    public SpringSocialConfigurer springSocialConfigurer() {
        CustomSpringSocialConfigurer customSpringSocialConfigurer = new CustomSpringSocialConfigurer(securityProperties.getSocial().getFilterProcessesUrl());
        customSpringSocialConfigurer.signupUrl(securityProperties.getSocial().getSignUpUrl());
        return customSpringSocialConfigurer;
    }
    @Bean
    public ConnectController connectController(
            ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }

    /**
     * 在注册前操作OAuth2授权的数据，以及在注册后将数据存储到Connection表中的工具类
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }
}
