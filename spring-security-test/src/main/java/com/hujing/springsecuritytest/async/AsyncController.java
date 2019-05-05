package com.hujing.springsecuritytest.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author hj
 * @create 2019-05-01 13:43
 */
@RestController
@Slf4j
public class AsyncController {

    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @GetMapping("/async")
    public DeferredResult<String> async() {
        //1.生成订单然后下单
        log.info("【主线程开始】");
        String orderId = RandomStringUtils.randomNumeric(8);
        DeferredResult<String> dr = new DeferredResult<>();
        dr.setResult(orderId);
        deferredResultHolder.getMap().put(orderId, dr);
        //模拟消息队列
        mockQueue.setPlaceOrder(orderId);
        log.info("【主线程结束】");
        return dr;
    }

}
