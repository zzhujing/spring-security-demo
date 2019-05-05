package com.hujing.springsecuritytest.async;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hj
 * @create 2019-05-01 13:46
 */
@Data
@Component
public class DeferredResultHolder {
    private final Map<String, DeferredResult<String>> map = new HashMap<>();
}
