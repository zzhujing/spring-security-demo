package com.hujing.springsecuritycore.code.sms;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author hj
 * @create 2019-05-01 22:30
 * 图形校验码
 */
@Data
@Builder
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 8210774563804307861L;
    private String code;
    private LocalDateTime expireTime;

    public ValidateCode() {
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 传入过期的秒数构造过期时间
     *
     * @param code
     * @param expire
     */
    public ValidateCode(String code, int expire) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
    }

    /**
     * 判断是否过期
     *
     * @return
     */
    public boolean isExpire() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
