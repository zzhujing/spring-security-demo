package com.hujing.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author hj   2019-05-05 16:17
    授权服务器的授权相关配置类
 */
@Configuration
@EnableAuthorizationServer
public class SsoServerAuthenticationConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * token转化器
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("signKey");
        return converter;
    }
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    /**
     * 第三方应用相关配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("qq")
                .secret(bCryptPasswordEncoder.encode("qq"))
                .authorizedGrantTypes("refresh_token","authorization_code")
                .accessTokenValiditySeconds(7200)
                .redirectUris("http://localhost:8080/client1/login","http://localhost:8060/client2/login")
                .scopes("all")
                .and()
                .withClient("weChat")
                .secret(bCryptPasswordEncoder.encode("weChat"))
                .authorizedGrantTypes("refresh_token","authorization_code")
                .accessTokenValiditySeconds(7200)
                .redirectUris("http://localhost:8060/client2/login","http://localhost:8080/client1/login")
                .scopes("all");
    }

    /**
     * 设置token相关信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
    }

    /**
     * 配置授权服务器的认证策略
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //第三方应用需要获取token.signKey，这里配置获取signKey需要认证。
        security.tokenKeyAccess("isAuthenticated()");
        //设置认真策略的加密方式
        security.passwordEncoder(bCryptPasswordEncoder);
    }
}
