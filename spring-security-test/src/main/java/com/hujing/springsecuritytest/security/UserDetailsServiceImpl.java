package com.hujing.springsecuritytest.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author hj
 * @create 2019-05-01 17:27
 * 自定义认证授权
 */
@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //这里根据用户名去和数据库交互
        //这里也可以使用自定UserDetails类进行更加详细的定制和返回
        log.info("【表单登录，username : {}】", s);
        return new CustomUserDetails(s, bCryptPasswordEncoder.encode("hujing"), AuthorityUtils.createAuthorityList("admin"));
    }
    /**
     * 社交登录
     * @param s connection.userId
     * @return 根据 userId->构建出用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
        log.info("【社交登录，username : {}】", s);
        return new SocialUser(s, bCryptPasswordEncoder.encode("hujing"), AuthorityUtils.createAuthorityList("admin,ROLE_USER"));
    }
}
