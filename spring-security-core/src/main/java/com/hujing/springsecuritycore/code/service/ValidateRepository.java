package com.hujing.springsecuritycore.code.service;

import com.hujing.springsecuritycore.code.sms.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author hj
 * @create 2019-05-04 19:50
 * 验证码存储操作接口
 */
public interface ValidateRepository {

    /**
     * 保存验证码
     *
     * @param request
     * @param result
     * @param type
     */
    void save(ServletWebRequest request, ValidateCode result, String type);


    /**
     * 获取验证码
     *
     * @param request
     * @param type
     * @return
     */
    ValidateCode get(ServletWebRequest request, String type);

    /**
     * 移除验证码
     *
     * @param request
     * @param type
     */
    void remove(ServletWebRequest request, String type);
}
