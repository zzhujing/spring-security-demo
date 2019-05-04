package com.hujing.springsecuritycore.properties;

import lombok.Data;

/**
 * @author hj
 * @create 2019-05-04 21:52
 */
@Data
public class ClientProperties {
    private String clientId;
    private String clientSecret;
    private int accessTokenValiditySeconds = 7200; //token超时时间 ，默认2小时
}
