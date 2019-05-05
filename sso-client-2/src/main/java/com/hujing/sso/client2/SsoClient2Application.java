package com.hujing.sso.client2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hj   2019-05-05 16:39
 */
@SpringBootApplication
@EnableOAuth2Sso //开启单点登录
@RestController
public class SsoClient2Application {
    /**
     * 返回用户信息
     * @param user
     * @return
     */
    @GetMapping("/user")
    public Authentication getUser(Authentication user) {
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoClient2Application.class, args);
    }
}
