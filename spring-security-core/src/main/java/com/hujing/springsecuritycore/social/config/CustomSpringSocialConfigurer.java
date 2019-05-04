package com.hujing.springsecuritycore.social.config;


import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author hj
 * @create 2019-05-03 13:51
 */
public class CustomSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public CustomSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
    @SuppressWarnings("unchecked")
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        //设置spring social filter 默认拦截前缀，以前是/auth
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}
