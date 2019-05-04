package com.hujing.springsecuritycore.code.image;

import com.hujing.springsecuritycore.code.sms.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author hj
 * @create 2019-05-01 22:30
 * 图形校验码
 */
@Data
public class ImageCode extends ValidateCode {

    private static final long serialVersionUID = 7614616334824165586L;

    private BufferedImage image;

    public ImageCode() {
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code,expireTime);
        this.image = image;
    }
    /**
     * 传入过期的秒数构造过期时间
     * @param image
     * @param code
     * @param expire
     */
    public ImageCode(BufferedImage image, String code, int expire) {
        super(code,expire);
        this.image = image;
    }
}
