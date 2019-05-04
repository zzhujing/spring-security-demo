package com.hujing.springsecuritytest.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author hj
 * @create 2019-05-01 13:51
 */
@Component
@Slf4j
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(() -> {
            while (true) {
                String orderNum = mockQueue.getCompletOrder();
                if (StringUtils.isNotBlank(orderNum)) {
                    deferredResultHolder.getMap().get(orderNum).setResult(orderNum);
                    log.info("【返回订单处理结果】 orderNum:{}", orderNum);
                    mockQueue.setCompletOrder(null);
                } else {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
