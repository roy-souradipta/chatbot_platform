package com.chatbot.entities;

import java.util.List;
import java.util.UUID;

public class ChatConversation {

    private UUID id;

    private String domainId;

    private String conversationState;

    private List<Intent> intents;

    private List<IntentParameter> intentParameters;

    public ChatConversation(UUID id,
                            String domainId,
                            String conversationState,
                            List<Intent> intents,
                            List<IntentParameter> intentParameters) {
        this.id = id;
        this.domainId = domainId;
        this.conversationState = conversationState;
        this.intents = intents;
        this.intentParameters = intentParameters;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getConversationState() {
        return conversationState;
    }

    public void setConversationState(String conversationState) {
        this.conversationState = conversationState;
    }

    public List<Intent> getIntents() {
        return intents;
    }

    public void setIdentifiedIntent(List<Intent> intents) {
        this.intents = intents;
    }

    public List<IntentParameter> getIntentParameters() {
        return intentParameters;
    }

    public void setIntentParameters(List<IntentParameter> intentParameters) {
        this.intentParameters = intentParameters;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }
}
