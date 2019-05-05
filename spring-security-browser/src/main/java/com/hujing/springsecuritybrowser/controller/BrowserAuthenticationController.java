package com.hujing.springsecuritybrowser.controller;

import com.hujing.springsecuritybrowser.SocialInfo;
import com.hujing.springsecuritycore.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hj
 * @create 2019-05-01 18:24
 * 统一中转处理登录请求
 */
@RestController
@Slf4j
public class BrowserAuthenticationController {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    //获取上个请求
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @GetMapping("/authentication/required")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String authenticationSignIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String target = savedRequest.getRedirectUrl();
            log.info("【请求访问资源的url为】 : {} ", target);
            if (StringUtils.endsWithIgnoreCase(target, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return "您还没有经过认证，请登录，谢谢~";
    }

    /**
     * 获取授权后的SocialInfo信息，实际就是从 session中取出
     * @return
     */
    @GetMapping("/social/user")
    public SocialInfo getSocialInfo(HttpServletRequest request) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        return SocialInfo.builder()
                .providerId(connection.getKey().getProviderId())
                .providerUserId(connection.getKey().getProviderUserId())
                .nickname(connection.getDisplayName())
                .headimg(connection.getImageUrl())
                .build();
    }


    /**
     * session 失效的响应
     * @return
     */
    @GetMapping("/session/invalid")
    public String sessionInvalid() {
        return "session invalid...";
    }
}
