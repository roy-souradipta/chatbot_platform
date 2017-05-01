package com.chatbot.dtos;

import com.chatbot.entities.Intent;
import com.chatbot.entities.IntentParameter;

import java.util.List;
import java.util.UUID;

public class NLPResponseDto {

    private UUID responseMessageId;

    private String chatMessageId;

    private String currentConversationId;

    private String currentSessionId;

    private String lastConversationState;

    private String responseText;

    private Intent[] intents;

    private List<IntentParameter> parameters;

    private String appId;

    private String domainId;

    private String parameterFulFillmentIndicator;

    public NLPResponseDto() {
    }


    public UUID getResponseMessageId() {
        return responseMessageId;
    }

    public void setResponseMessageId(UUID responseMessageId) {
        this.responseMessageId = responseMessageId;
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

    public String getLastConversationState() {
        return lastConversationState;
    }

    public void setLastConversationState(String lastConversationState) {
        this.lastConversationState = lastConversationState;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public List<IntentParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<IntentParameter> parameters) {
        this.parameters = parameters;
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

    public String getParameterFulFillmentIndicator() {
        return parameterFulFillmentIndicator;
    }

    public void setParameterFulFillmentIndicator(String parameterFulFillmentIndicator) {
        this.parameterFulFillmentIndicator = parameterFulFillmentIndicator;
    }

    public Intent[] getIntents() {
        return intents;
    }

    public void setIntents(Intent[] intents) {
        this.intents = intents;
    }
}
