package com.hujing.springsecuritycore.social.qq.connet;

import com.hujing.springsecuritycore.social.qq.api.QQ;
import com.hujing.springsecuritycore.social.qq.api.impl.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author hj
 * @create 2019-05-03 11:18
 * 操作qq oauth2 整个授权过程 泛型为获取用户信息的API接口名
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
    private String appId;

    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));
        this.appId = appId;
    }
    /**
     * @param accessToken accessToken
     * @return 系统获取qq用户信息实例
     */
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
