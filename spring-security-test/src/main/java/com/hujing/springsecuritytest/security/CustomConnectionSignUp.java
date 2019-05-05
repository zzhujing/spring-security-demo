package com.hujing.springsecuritytest.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author hj
 * @create 2019-05-03 16:59
    自定义ConnectionSignUp 让第三方信息生成默认账号
 */
@Component
public class CustomConnectionSignUp implements ConnectionSignUp {
    @Override
    public String execute(Connection<?> connection) {
        //使用第三方名称生成：比如qq昵称
        return connection.getDisplayName();
    }
}
