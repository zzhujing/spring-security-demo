package com.hujing.springsecuritycore.social.qq.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujing.springsecuritycore.social.qq.api.QQ;
import com.hujing.springsecuritycore.social.qq.model.QQUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @author hj
 * @create 2019-05-03 10:49
 * 获取QQ用户信息实现类
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private String appId;
    private String openid;
    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER); //指定拼装token到请求参数位置
        this.appId = appId;
        String result = getRestTemplate().getForObject(String.format(URL_GET_OPENID, accessToken), String.class);
        log.info("【获取到用户信息 user : {}】",result);
        this.openid = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        String result = getRestTemplate().getForObject(String.format(URL_GET_USERINFO, appId, openid), String.class);
        QQUserInfo info = null;
        try {
            info = objectMapper.readValue(result, QQUserInfo.class);
            info.setOpenId(openid);
            return info;
        } catch (IOException e) {
            log.warn("【QQUSerInfo转化json失败】");
        }
        return null;
    }
}
