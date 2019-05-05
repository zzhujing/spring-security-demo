package com.hujing.springsecuritycore.social.qq.connet;

import com.hujing.springsecuritycore.social.qq.api.QQ;
import com.hujing.springsecuritycore.social.qq.model.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author hj
 * @create 2019-05-03 11:55
 */
public class QQAdapter implements ApiAdapter<QQ> {
    /**
     * 检测获取用户信息的api是否可用
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }
    /**
     * 服务商获取的 userInfo -> spring social 的 connection
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null); //主页url
        values.setProviderUserId(userInfo.getOpenId());
    }
    /**
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }
    /**
     * 更新主页信息
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(QQ api, String message) {}
}
