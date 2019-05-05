package com.hujing.springsecuritycore.code.effective;

import com.hujing.springsecuritycore.code.image.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @author hj
 * @create 2019-05-02 17:07
 * 图形验证码处理器
 */
@Component
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    public void send(ServletWebRequest request, ImageCode code) throws IOException {
        ImageIO.write(code.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
