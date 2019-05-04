package com.hujing.springsecurityapp.config;

import com.hujing.springsecuritycore.properties.ClientProperties;
import com.hujing.springsecuritycore.properties.SecurityProperties;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.List;

/**
 * @author hj
 * @create 2019-05-04 0:05
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDetailsService userDetailsServiceImpl;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    /**
     * 使用redis存储token,也可以使用数据库存储。
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    private final AuthenticationManager authenticationManager;

    //初始化 AuthenticationManager
    public AuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsServiceImpl)
                .tokenStore(tokenStore())
        ;
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        List<ClientProperties> clientList = securityProperties.getOauth2().getClients();
        if (CollectionUtils.isNotEmpty(clientList)) {
            clientList.forEach(clientProp -> {
                builder
                        .withClient(clientProp.getClientId())
                        .secret(bCryptPasswordEncoder.encode(clientProp.getClientSecret()))
                        .accessTokenValiditySeconds(clientProp.getAccessTokenValiditySeconds())
                        .authorizedGrantTypes("authorization_code", "password")
                        .scopes("app");
            });
        }
    }
}
