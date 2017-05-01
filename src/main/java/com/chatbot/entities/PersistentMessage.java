package com.chatbot.entities;

import java.sql.Timestamp;
import java.util.UUID;

public class PersistentMessage {

    private UUID messageId;

    private UUID conversationId;

    private UUID sessionId;

    private String appId;

    private String chatString;

    private String responseString;

    private ProcessingStatus processingStatus;

    private Timestamp queryReceivedTime;

    public PersistentMessage(UUID messageId,
                             UUID conversationId,
                             UUID sessionId,
                             String appId,
                             String chatString,
                             String responseString,
                             ProcessingStatus processingStatus,
                             Timestamp queryReceivedTime){
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.sessionId = sessionId;
        this.appId = appId;
        this.chatString = chatString;
        this.responseString = responseString;
        this.processingStatus = processingStatus;
        this.queryReceivedTime = queryReceivedTime;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public String getChatString() {
        return chatString;
    }

    public void setChatString(String chatString) {
        this.chatString = chatString;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public ProcessingStatus getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(ProcessingStatus processingStatus) {
        this.processingStatus = processingStatus;
    }

    public Timestamp getQueryReceivedTime() {
        return queryReceivedTime;
    }

    public void setQueryReceivedTime(Timestamp queryReceivedTime) {
        this.queryReceivedTime = queryReceivedTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
