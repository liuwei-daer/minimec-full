package com.allyes.minimec.api.rabbitmq;

import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author liuwei
 * @date 2018-03-16
 */
@Slf4j
@Component
public class Sender {

//    @Autowired
//    private AmqpTemplate rabbitTemplate;

    public void send(String msg) {
        String context = "hello "+ msg + " ----> time is :" + new Date();
        log.info("Sender : " + context);
//        this.rabbitTemplate.convertAndSend("mqtt-test", context);
    }

}