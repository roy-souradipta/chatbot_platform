package com.chatbot.service.nlp;

import com.chatbot.dtos.NLPRequestDto;
import com.chatbot.exception.JsonParsingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NLPMessageSender {

    private RabbitTemplate rabbitTemplate;

    private DirectExchange exchange;

    @Autowired
    public NLPMessageSender(ConnectionFactory rabbitConnection, DirectExchange exchange){
        rabbitTemplate = new RabbitTemplate(rabbitConnection);
        this.exchange = exchange;
    }

    public void sendRequestMessageToNlp(NLPRequestDto nlpRequestDto) {
        String nlpRequestMessage = null;
        try {
            nlpRequestMessage = new ObjectMapper().writeValueAsString(nlpRequestDto);
        }catch(IOException ex){
            throw new JsonParsingException("Unable to parse to Json message");
        }
        rabbitTemplate.convertAndSend(exchange.getName(), "nlp.request.key", nlpRequestMessage);
    }
}
