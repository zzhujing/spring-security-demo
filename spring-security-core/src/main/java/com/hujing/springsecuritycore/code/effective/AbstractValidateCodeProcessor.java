package com.hujing.springsecuritycore.code.effective;

import com.hujing.springsecuritycore.code.exception.ValidateCodeException;
import com.hujing.springsecuritycore.code.service.ValidateCodeGenerator;
import com.hujing.springsecuritycore.code.service.ValidateRepository;
import com.hujing.springsecuritycore.code.sms.ValidateCode;
import com.hujing.springsecuritycore.enums.CodeType;
import com.hujing.springsecuritycore.utils.SpringContextHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author hj
 * @create 2019-05-02 14:16
 */
public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     * 将spring ioc 容器中所有的code processor 全部注入到该map 中
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

    @Autowired
    private ValidateRepository validateRepository;

    @Override
    public void createCode(ServletWebRequest request) throws Exception {
        //1.生成校验码
        T code = generator(request);
        //2.添加到session域
        save(request, code);
        //3.发送code
        send(request, code);
    }

    /**
     * 根据不同的codeGenerator生成不同的code对象
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private T generator(ServletWebRequest request) {
        String type = getProcessorTypeByRequestURI(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(type + "CodeGenerator");
        return (T) validateCodeGenerator.generator(request.getRequest());
    }

    /**
     * 将code放入session域中
     *
     * @param request
     * @param code
     */
    private void save(ServletWebRequest request, T code) {
        String type = getProcessorTypeByRequestURI(request);
        SessionStrategy sessionStrategy = SpringContextHolder.getBean(SessionStrategy.class);
        //不将ImageCode放入session中
        ValidateCode result = new ValidateCode(code.getCode(), code.getExpireTime());
        validateRepository.save(request, result, type);
    }

    /**
     * 发送
     *
     * @param request
     * @param code
     */
    public abstract void send(ServletWebRequest request, T code) throws Exception;

    /**
     * 根据requestURI获取processor类型
     *
     * @param request
     * @return
     */
    private String getProcessorTypeByRequestURI(ServletWebRequest request) {
        return StringUtils.substringBefore(StringUtils.uncapitalize(getClass().getSimpleName()), "CodeProcessor");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validateCode(ServletWebRequest request) throws ServletRequestBindingException {
        //校验
        String type = getProcessorTypeByRequestURI(request);
        CodeType codeType = CodeType.valueOf(type.toUpperCase());
        ValidateCode code = validateRepository.get(request, type);
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), codeType.getMessage());
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if (code == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (code.isExpire()) {
            validateRepository.remove(request, type);
            throw new ValidateCodeException("验证码已经过期");
        }
        if (!StringUtils.equalsIgnoreCase(code.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不正确");
        }
        validateRepository.remove(request, type);
    }
}
