package com.hujing.springsecuritytest.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author hj
 * @create 2019-05-01 17:21
 */
public class CustomUserDetails extends User {
    //注入dao
    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    //根据数据库信息和业务逻辑判断是否冻结以及过期等。
    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }
    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }
    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }
}
