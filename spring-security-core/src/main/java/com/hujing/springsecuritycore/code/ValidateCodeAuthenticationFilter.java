package com.hujing.springsecuritycore.code;

import com.hujing.springsecuritycore.code.effective.ValidateCodeProcessorHolder;
import com.hujing.springsecuritycore.code.exception.ValidateCodeException;
import com.hujing.springsecuritycore.enums.CodeType;
import com.hujing.springsecuritycore.properties.SecurityProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author hj
 * @create 2019-05-02 22:21
 * 公用的校验拦截器
 */
@Slf4j
@Component
@Data
public class ValidateCodeAuthenticationFilter extends OncePerRequestFilter implements InitializingBean {
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private SessionStrategy sessionStrategy;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AntPathMatcher pathMatcher;
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    //存放资源和资源类型的对应关系map
    private Map<String, CodeType> urlMap = new HashMap<>();

    /**
     * 初始化所有验证码的校验路径
     *
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlMap.put("/authentication/mobile", CodeType.SMS);
        urlMap.put("/authentication/signIn", CodeType.IMAGE);
        addInterceptorUrlPathList(securityProperties);
    }

    /**
     * 将配置的需要校验的地址添加到map中
     *
     * @param securityProperties
     */
    private void addInterceptorUrlPathList(SecurityProperties securityProperties) {
        Set<String> smsPathList = securityProperties.getCode().getSms().getInterceptPathList();
        Set<String> imagePathList = securityProperties.getCode().getImage().getInterceptPathList();
        if (CollectionUtils.isNotEmpty(smsPathList)) {
            smsPathList.forEach(smsPath -> {
                urlMap.put(smsPath, CodeType.SMS);
            });
        }
        if (CollectionUtils.isNotEmpty(imagePathList)) {
            imagePathList.forEach(imagePath -> {
                urlMap.put(imagePath, CodeType.IMAGE);
            });
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.根据请求url,获取请求类型
        CodeType type = findCodeTypeByRequest(request);
        if (type != null) {
            try {
                log.info("【开始校验请求：{}】，类型是：{}", request.getRequestURI(), type);
                //根据请求类型进行校验处理
                validateCodeProcessorHolder.findProcessorByType(type).validateCode(new ServletWebRequest(request));
                log.info("【校验请求成功】");
            } catch (ValidateCodeException e) {
                log.warn("【请求校验失败】 e : {} ", e.getMessage());
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        //放行
        filterChain.doFilter(request,response);
    }

    /**
     * 根据request获取请求类型
     *
     * @param request
     * @return
     */
    private CodeType findCodeTypeByRequest(HttpServletRequest request) {
        CodeType type = null;
        Set<String> urlPathSet = urlMap.keySet();
        for (String path : urlPathSet) {
            if (StringUtils.equals(request.getRequestURI(), path)) {
                type = urlMap.get(path);
            }
        }
        return type;
    }
}
