package com.hujing.springsecuritycore.social.qq.connet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;

/**
 * @author hj
 * @create 2019-05-03 15:20
 * 自定义Oauth2Template 解决qq 传递参数格式问题，以及默认不开启Client
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {
    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        //需要开启Client,否则不会传递clientId,ClientSecret参数
        super.setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String qqParamesResult = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        String[] paramArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(qqParamesResult, "&");
        if (ArrayUtils.isEmpty(paramArr) || paramArr.length != 3) {
            log.warn("【qq回调得到的access_token参数不合法。】");
            return null;
        }
        String accessToken = StringUtils.substringAfter(paramArr[0], "=");
        String expiresIn = StringUtils.substringAfter(paramArr[1], "=");
        String refreshToken = StringUtils.substringAfter(paramArr[2], "=");
        return new AccessGrant(accessToken, null, refreshToken, Long.parseLong(expiresIn));
    }
}
