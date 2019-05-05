package com.hujing.springsecuritycore.code.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author hj
 * @create 2019-05-01 23:19
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String explanation) {
        super(explanation);
    }
}
