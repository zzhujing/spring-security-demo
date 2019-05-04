package com.hujing.springsecuritycore.social.qq.api;

import com.hujing.springsecuritycore.social.qq.model.QQUserInfo;

/**
 * @author hj
 * @create 2019-05-03 10:47
    使用accessToken换取QQ用户信息接口
 */
public interface QQ {
    QQUserInfo getUserInfo();
}
