package com.hujing.springsecuritycore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SpringSecurityCoreApplicationTests {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("hujing"));
    }

}
