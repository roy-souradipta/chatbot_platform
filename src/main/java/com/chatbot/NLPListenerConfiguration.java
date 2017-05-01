package com.chatbot;

import com.chatbot.service.nlp.NLPMessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NLPListenerConfiguration {

    @Autowired
    private NLPMessageListener nlpMessageReceiver;

    @Autowired
    private Queue nlpListenerQueue;

    @Autowired
    private RabbitConfigurationProperties rabbitConfigurationProperties;

    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitConnectionFactory);
        container.setQueueNames(nlpListenerQueue.getName());
        container.setMessageListener(messageListenerAdapter());
        return container;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(nlpMessageReceiver, new Jackson2JsonMessageConverter());
    }
}
