package com.hujing.springsecuritycore.properties;

import lombok.Data;

/**
 * @author hj
 * @create 2019-05-03 12:32
 */
@Data
public class QQProperties {
    private String appId;
    private String appSecret;
    private String providerId = "qq";
}
