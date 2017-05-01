package com.chatbot.service.general;

import com.chatbot.dtos.NLPResponseDto;
import com.chatbot.service.nlp.NLPMessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class AsyncMessageHandler {

    public static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2, 4, 10,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2));

    public static void handleNlpMessage(Message message , NLPMessageHandler nlpMessageHandler){
        executor.execute(() -> {
            try {
                nlpMessageHandler.handleMessage(new ObjectMapper().readValue(message.getBody(), NLPResponseDto.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
