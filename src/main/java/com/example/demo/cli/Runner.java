package com.example.demo.cli;

import com.example.demo.RabbitMqDemoApplication;
import com.example.demo.Receiver;
import com.example.demo.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by jerry on 2017/12/1.
 */
@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate template;
    private final Receiver receiver;
    private final ConfigurableApplicationContext context;

    public Runner(Receiver receiver,
                  RabbitTemplate template,
                  ConfigurableApplicationContext context) {
        this.receiver = receiver;
        this.template = template;
        this.context = context;
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("發送訊息...");
        template.convertAndSend(RabbitMQConfig.QUEUE_NAME, "Hello World from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MICROSECONDS);
        context.close();
    }
}
