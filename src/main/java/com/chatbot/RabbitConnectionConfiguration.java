package com.chatbot;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConnectionConfiguration {

    @Autowired
    RabbitConfigurationProperties rabbitConfigurationProperties;

    @Bean
    public ConnectionFactory getInternalRabbitConnectionFactory()
    {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
                rabbitConfigurationProperties.getHostname());
        connectionFactory.setUsername(rabbitConfigurationProperties.getUsername());
        connectionFactory.setPassword(rabbitConfigurationProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    DirectExchange internalRabbitExchange() {
        return new DirectExchange(rabbitConfigurationProperties.getExchange(), true, false);
    }

    @Bean
    public Queue nlpListenerQueue() {
        return new Queue(rabbitConfigurationProperties.getTopics(), true);
    }

    @Bean
    Binding nlpBinding(DirectExchange internalExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(internalExchange).with(
                rabbitConfigurationProperties.getNlpreceiverroutingkey());
    }
}
