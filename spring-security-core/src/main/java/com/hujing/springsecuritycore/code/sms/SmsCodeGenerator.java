package com.hujing.springsecuritycore.code.sms;

import com.hujing.springsecuritycore.code.service.ValidateCodeGenerator;
import com.hujing.springsecuritycore.properties.SecurityProperties;
import com.hujing.springsecuritycore.properties.SmsCodeProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hj
 * @create 2019-05-02 13:32
    生成短信验证码逻辑
 */
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generator(HttpServletRequest request) {
        SmsCodeProperties sms = securityProperties.getCode().getSms();
        String smsCode = RandomStringUtils.randomNumeric(sms.getLength());
        return new ValidateCode(smsCode, sms.getExpireIn());
    }
}
