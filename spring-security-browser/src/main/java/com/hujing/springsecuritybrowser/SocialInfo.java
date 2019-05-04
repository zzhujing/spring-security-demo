package com.hujing.springsecuritybrowser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hj
 * @create 2019-05-03 16:12
 * 注册时给用户展示Oauth2相关属性实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialInfo {

    private String providerId;

    private String providerUserId;

    private String nickname;

    private String headimg;
}
