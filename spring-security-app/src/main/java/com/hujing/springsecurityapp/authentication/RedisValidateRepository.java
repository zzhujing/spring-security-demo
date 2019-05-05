package com.hujing.springsecurityapp.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujing.springsecuritycore.code.exception.ValidateCodeException;
import com.hujing.springsecuritycore.code.service.ValidateRepository;
import com.hujing.springsecuritycore.code.sms.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author hj
 * @create 2019-05-04 19:56
 * 基于Redis实现的验证码操作类
 */
@Component
public class RedisValidateRepository implements ValidateRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void save(ServletWebRequest request, ValidateCode result, String type) {
        //从请求头中获取设备Id
        String key = buildKey(request, type);
        try {
            stringRedisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(result),30, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            throw new ValidateCodeException("存储验证码失败");
        }
    }

    @Override
    public ValidateCode get(ServletWebRequest request, String type) {
        String jsonResult = stringRedisTemplate.opsForValue().get(buildKey(request, type));
        try {
            return objectMapper.readValue(jsonResult, ValidateCode.class);
        } catch (IOException e) {
            throw new ValidateCodeException("获取验证码失败");
        }

    }

    @Override
    public void remove(ServletWebRequest request, String type) {
        stringRedisTemplate.delete(buildKey(request, type));
    }



    /**
     * 构建存储id
     * @param request
     * @param type
     * @return
     */
    private String buildKey(ServletWebRequest request, String type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请求头中deviceId不能为空");
        }
        return "code:" + type + ":" + deviceId;
    }
}
