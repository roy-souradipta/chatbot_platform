package com.chatbot.dtos;

import com.chatbot.state.States;
import com.chatbot.entities.ChatConversation;
import com.chatbot.entities.PersistentMessage;

import java.util.UUID;

public class NLPRequestDto {

    private UUID requestMessageId;

    private String chatMessageId;

    private String currentConversationId;

    private String currentSessionId;

    private String currentConversationState;

    private String requestText;

    private String appId;

    private String domainId;

    public NLPRequestDto() {
    }

    public NLPRequestDto(UUID requestMessageId,
                         String chatMessageId,
                         String currentConversationId,
                         String currentSessionId,
                         String currentConversationState,
                         String requestText,
                         String appId,
                         String domainId) {
        this.requestMessageId = requestMessageId;
        this.chatMessageId = chatMessageId;
        this.currentConversationId = currentConversationId;
        this.currentSessionId = currentSessionId;
        this.currentConversationState = currentConversationState;
        this.requestText = requestText;
        this.appId = appId;
        this.domainId = domainId;
    }

    public UUID getRequestMessageId() {
        return requestMessageId;
    }

    public void setRequestMessageId(UUID requestMessageId) {
        this.requestMessageId = requestMessageId;
    }

    public String getChatMessageId() {
        return chatMessageId;
    }

    public void setChatMessageId(String chatMessageId) {
        this.chatMessageId = chatMessageId;
    }

    public String getCurrentConversationId() {
        return currentConversationId;
    }

    public void setCurrentConversationId(String currentConversationId) {
        this.currentConversationId = currentConversationId;
    }

    public String getCurrentSessionId() {
        return currentSessionId;
    }

    public void setCurrentSessionId(String currentSessionId) {
        this.currentSessionId = currentSessionId;
    }

    public String getCurrentConversationState() {
        return currentConversationState;
    }

    public void setCurrentConversationState(String currentConversationState) {
        this.currentConversationState = currentConversationState;
    }

    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }


    public static NLPRequestDto from(PersistentMessage persistentMessage, ChatConversation chatConversation) {
        return new NLPRequestDto(UUID.randomUUID(),
                persistentMessage.getMessageId().toString(),
                persistentMessage.getConversationId().toString(),
                persistentMessage.getSessionId().toString(),
                chatConversation.getConversationState(),
                persistentMessage.getChatString(),
                persistentMessage.getAppId(),
                chatConversation.getDomainId());

    }

    public static NLPRequestDto from(PersistentMessage persistentMessage) {
        return new NLPRequestDto(UUID.randomUUID(),
                persistentMessage.getMessageId().toString(),
                null,
                persistentMessage.getSessionId().toString(),
                States.PRIMITIVE.toString(),
                persistentMessage.getChatString(),
                persistentMessage.getAppId(),
                null);
    }
}
