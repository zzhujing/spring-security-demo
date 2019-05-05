package com.hujing.springsecuritycore.code;

import com.hujing.springsecuritycore.code.effective.ValidateCodeProcessorHolder;
import com.hujing.springsecuritycore.code.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hj
 * @create 2019-05-01 22:35
 */
@RestController
@Slf4j
public class ValidateCodeController {

    public static final String SESSION_KEY = "IMAGE_CODE_SESSION_KEY";

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 返回验证码图片
     *
     * @param request
     * @param response
     */
    @GetMapping("/code/{type}")
    public void createCode(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) {
        try {
            validateCodeProcessorHolder.findProcessorByType(type).createCode(new ServletWebRequest(request,response));
        } catch (Exception e) {
            throw new ValidateCodeException("返回验证码失败");
        }
    }
}
