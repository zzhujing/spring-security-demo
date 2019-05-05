package com.hujing.springsecuritycore.properties;

import com.hujing.springsecuritycore.enums.LoginType;
import lombok.Data;

/**
 * @author hj
 * @create 2019-05-01 18:38
 */
@Data
public class BrowserProperties {
    private String loginPage = "/browserProperties-signIn.html"; //没有指定则去访问默认的登录认证页面
    private LoginType loginType = LoginType.JSON;  //默认返回Json
    private int rememberMeExpire = 7 * 24 * 60 * 60; //默认为7天
}
