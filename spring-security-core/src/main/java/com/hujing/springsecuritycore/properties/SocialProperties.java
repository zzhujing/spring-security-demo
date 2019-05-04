package com.hujing.springsecuritycore.properties;

import lombok.Data;

/**
 * @author hj
 * @create 2019-05-03 12:38
 */
@Data
public class SocialProperties {
    private String filterProcessesUrl = "/auth"; //默认spring social filter 拦截前缀
    private String signUpUrl = "/signUp";
    private QQProperties qq = new QQProperties();
}
