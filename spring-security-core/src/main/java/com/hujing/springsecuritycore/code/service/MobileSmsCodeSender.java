package com.hujing.springsecuritycore.code.service;

/**
 * @author hj
 * @create 2019-05-02 13:15
    发送短信接口,如果要扩展，直接实现并且添加到容器中即可
 */
public interface MobileSmsCodeSender {
    void send(String mobile, String code);
}
