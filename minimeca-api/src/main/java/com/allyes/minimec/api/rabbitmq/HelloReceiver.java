package com.allyes.minimec.api.rabbitmq;

//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liuwei
 * @date 2018-03-16
 */
@Component
//@RabbitListener(queues = "mqtt-test")
public class HelloReceiver {

//    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }

}