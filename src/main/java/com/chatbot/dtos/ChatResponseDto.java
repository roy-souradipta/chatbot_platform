package com.chatbot.dtos;

import com.chatbot.entities.ChatConversation;
import com.chatbot.entities.Intent;
import com.chatbot.entities.IntentParameter;
import com.chatbot.entities.PersistentMessage;

import java.util.List;

public class ChatResponseDto {

    private String messageId;

    private String sessionId;

    private String conversationId;

    private String responseString;

    public ChatResponseDto(String messageId,
                           String sessionId,
                           String conversationId,
                           String responseString,
                           List<Intent> intents,
                           List<IntentParameter> intentParameters) {
        this.messageId = messageId;
        this.sessionId = sessionId;
        this.conversationId = conversationId;
        this.responseString = responseString;
        this.intents = intents;
        this.intentParameters = intentParameters;
    }

    private List<Intent> intents;

    private List<IntentParameter> intentParameters;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public List<Intent> getIntents() {
        return intents;
    }

    public void setIntents(List<Intent> intents) {
        this.intents = intents;
    }

    public List<IntentParameter> getIntentParameters() {
        return intentParameters;
    }

    public void setIntentParameters(List<IntentParameter> intentParameters) {
        this.intentParameters = intentParameters;
    }

    public static ChatResponseDto from(ChatConversation chatConversation, PersistentMessage persistentMessage) {
        return new ChatResponseDto(persistentMessage.getMessageId().toString(),
                persistentMessage.getSessionId().toString(),
                persistentMessage.getConversationId().toString(),
                persistentMessage.getResponseString(),
                chatConversation.getIntents(),
                chatConversation.getIntentParameters());
    }

    public static ChatResponseDto from(PersistentMessage persistentMessage) {
        return new ChatResponseDto(persistentMessage.getMessageId().toString(),
                persistentMessage.getSessionId().toString(),
                null,
                persistentMessage.getResponseString(),
                null,
                null);
    }
}
