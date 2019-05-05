package com.hujing.springsecuritycore.social.qq.connet;

import com.hujing.springsecuritycore.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author hj
 * @create 2019-05-03 12:01
 * 创建Connection工厂类
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    /**
     * 构造工厂实例的构造函数
     * @param appId      qq账号的appId
     * @param appSecret  qq账号的appSecret
     * @param providerId 服务提供商id
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
