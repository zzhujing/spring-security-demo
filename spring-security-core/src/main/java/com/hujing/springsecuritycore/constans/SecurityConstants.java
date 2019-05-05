package com.hujing.springsecuritycore.constans;

/**
 * @author hj
 * @create 2019-05-05 23:30
 * 安全系统常量
 */
public interface SecurityConstants {

    //处理登录的url
    String LOGIN_PAGE_URL = "/authentication/required";

    //登录表单地址
    String LOGIN_PROCESSING_URL = "/authentication/signIn";

    //非法session跳转的地址
    String INVALID_SESSION_URL = "/session/invalid";

    //验证码地址 /code/{type}
    String VALIDATE_CODE_URL_ = "/code/*";

    //手机验证码登录表单提交地址
    String MOBILE_SIGNIN_URL = "/authentication/mobile";

    //用户注册表单提交地址
    String USER_REGISTER_URL = "/user/register";
}
