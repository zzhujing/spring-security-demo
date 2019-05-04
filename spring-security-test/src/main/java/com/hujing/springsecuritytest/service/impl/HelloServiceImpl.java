package com.hujing.springsecuritytest.service.impl;

import com.hujing.springsecuritytest.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author hj
 * @create 2019-05-01 11:07
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello,"+name;
    }
}
