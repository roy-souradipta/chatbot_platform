package com.chatbot.service.nlp;

import com.chatbot.service.general.AsyncMessageHandler;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NLPMessageListener implements MessageListener{

    private static final Logger logger = Logger.getLogger(NLPMessageListener.class);

    @Autowired
    private NLPMessageHandler nlpMessageHandler;


    @Override
    public void onMessage(Message message) {
       AsyncMessageHandler.handleNlpMessage(message, nlpMessageHandler);
    }
}
