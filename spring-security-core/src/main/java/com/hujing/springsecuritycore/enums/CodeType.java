package com.hujing.springsecuritycore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hj
 * @create 2019-05-02 18:59
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CodeType {

    IMAGE("imageCode"),
    SMS("smsCode"),
    ;

    private String message;

}