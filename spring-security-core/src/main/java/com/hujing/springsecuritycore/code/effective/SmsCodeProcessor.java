package com.hujing.springsecuritycore.code.effective;

import com.hujing.springsecuritycore.code.service.MobileSmsCodeSender;
import com.hujing.springsecuritycore.code.sms.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author hj
 * @create 2019-05-02 17:10
    短信code执行器
 */
@Component
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private MobileSmsCodeSender mobileSmsCodeSender;

    @Override
    public void send(ServletWebRequest request, ValidateCode code) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        mobileSmsCodeSender.send(mobile, code.getCode());
    }
}
