package com.hujing.springsecuritycore.code.effective;

import com.hujing.springsecuritycore.code.service.ValidateRepository;
import com.hujing.springsecuritycore.code.sms.ValidateCode;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author hj
 * @create 2019-05-04 20:00
 */
public class SessionValidateRepository implements ValidateRepository {

    private SessionStrategy sessionStrategy;

    public SessionValidateRepository(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    @Override
    public void save(ServletWebRequest request, ValidateCode result, String type) {
        sessionStrategy.setAttribute(request, type + ValidateCodeProcessor.VALIDATE_SESSION_KEY_SUFFIX, result);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, String type) {
        return (ValidateCode) sessionStrategy.getAttribute(request, type + ValidateCodeProcessor.VALIDATE_SESSION_KEY_SUFFIX);
    }

    @Override
    public void remove(ServletWebRequest request, String type) {
        sessionStrategy.removeAttribute(request, type + ValidateCodeProcessor.VALIDATE_SESSION_KEY_SUFFIX);
    }
}
