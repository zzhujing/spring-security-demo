package com.hujing.springsecuritytest.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author hj
 * @create 2019-05-01 13:40
 * 模拟消息队列
 */
@Slf4j
@Component
public class MockQueue {
    private String placeOrder;
    private String completOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {
        new Thread(() -> {
            this.placeOrder = placeOrder;
            log.info("【接受到请求准备下单】orderNum:{}", placeOrder);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completOrder = placeOrder;
            log.info("【处理请求成功】 orderNum:{}", completOrder);
        }).start();
    }

    public void setCompletOrder(String completOrder) {
        this.completOrder = completOrder;
    }

    public String getCompletOrder() {
        return completOrder;
    }
}
