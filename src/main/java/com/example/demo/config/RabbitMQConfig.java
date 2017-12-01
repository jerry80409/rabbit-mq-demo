package com.example.demo.config;

import com.example.demo.Receiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jerry on 2017/12/1.
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

    public final static String QUEUE_NAME = "RABBIT-MQ-QUEUE";
    public final static String TOPIC_EXCHANGE = "RABBIT-MQ-EXCHANGE";

    @Bean
    public Queue queue() {
        log.info("Init Queue: {}", QUEUE_NAME);
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * AMQP producers don’t send messages directly to queues. Instead, a message is sent to an exchange,
     * which can go to a single queue, or fanout to multiple queues, emulating the concept of JMS topics.
     * <p>
     * Ref: https://spring.io/understanding/AMQP
     *
     * @return
     */
    @Bean
    public TopicExchange exchange() {
        log.info("Init Topic-exchange: {}", TOPIC_EXCHANGE);
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        log.info("Binding Queue with topic-exchange");
        return BindingBuilder.bind(queue)
            .to(topicExchange)
            .with(QUEUE_NAME);
    }

    /**
     * The bean defined in the listenerAdapter() method is registered as a message listener
     * in the container defined in container()
     *
     * @param receiver class is a POJO
     * @return
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver) {
        // init MessageListenerAdapter(class, method)
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {
        log.info("Init SimpleMessageListenerContainer");
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
