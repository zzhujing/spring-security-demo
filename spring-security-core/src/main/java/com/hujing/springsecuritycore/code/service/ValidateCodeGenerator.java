package com.hujing.springsecuritycore.code.service;

import com.hujing.springsecuritycore.code.sms.ValidateCode;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hj
 * @create 2019-05-02 11:02
 * 生成图形验证码的接口
 */
public interface ValidateCodeGenerator {
    ValidateCode generator(HttpServletRequest request);
}
