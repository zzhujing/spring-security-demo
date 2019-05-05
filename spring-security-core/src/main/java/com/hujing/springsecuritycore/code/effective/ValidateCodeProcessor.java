package com.hujing.springsecuritycore.code.effective;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author hj
 * @create 2019-05-02 14:06
 */
public interface ValidateCodeProcessor {

    /**
     * session前缀
     */
    String VALIDATE_SESSION_KEY_SUFFIX= "_code_session_key";
    /**
     * 创建code的方法
     *
     * @param request
     */
    void createCode(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param request
     */
    void validateCode(ServletWebRequest request) throws ServletRequestBindingException;
}

