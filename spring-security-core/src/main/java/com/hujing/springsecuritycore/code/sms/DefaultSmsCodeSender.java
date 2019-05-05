package com.hujing.springsecuritycore.code.sms;

import com.hujing.springsecuritycore.code.service.MobileSmsCodeSender;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hj
 * @create 2019-05-02 13:16
 * 默认短信发送
 */
@Slf4j
public class DefaultSmsCodeSender implements MobileSmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.info("【给：{}发送短信成功，验证码为：{}】", mobile, code);
    }
}
