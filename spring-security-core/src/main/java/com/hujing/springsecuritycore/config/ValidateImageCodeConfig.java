package com.hujing.springsecuritycore.config;

import com.hujing.springsecuritycore.code.effective.SessionValidateRepository;
import com.hujing.springsecuritycore.code.image.DefaultImageCodeGenerator;
import com.hujing.springsecuritycore.code.service.MobileSmsCodeSender;
import com.hujing.springsecuritycore.code.service.ValidateCodeGenerator;
import com.hujing.springsecuritycore.code.service.ValidateRepository;
import com.hujing.springsecuritycore.code.sms.DefaultSmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;

/**
 * @author hj
 * @create 2019-05-02 11:12
 */
@Configuration
public class ValidateImageCodeConfig {
    /**
     * 配置条件，可以让后面想要扩展验证码生成的覆盖默认配置
     *
     * @return
     */
    @Bean("imageCodeGenerator")
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        return new DefaultImageCodeGenerator();
    }

    /**
     * 配置短信生成条件bean
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MobileSmsCodeSender.class)
    public MobileSmsCodeSender mobileSmsCodeSender() {
        return new DefaultSmsCodeSender();
    }

    @Bean
    public AntPathMatcher pathMatcher() {
        return new AntPathMatcher();
    }

    @Bean
    @DependsOn("springContextHolder")
    public SessionStrategy sessionStrategy() {
        return new HttpSessionSessionStrategy();
    }

    /**
     * 默认存储验证码的方式为session
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ValidateRepository.class)
    public ValidateRepository validateRepository() {
        return new SessionValidateRepository(sessionStrategy());
    }
}
