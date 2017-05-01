package com.chatbot.entities;

import java.util.UUID;

public class Intent {

    private UUID intentId;

    private String intentName;

    private String externalElementId;

    public Intent() {
    }

    public UUID getIntentId() {
        return intentId;
    }

    public void setIntentId(UUID intentId) {
        this.intentId = intentId;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getExternalElementId() {
        return externalElementId;
    }

    public void setExternalElementId(String externalElementId) {
        this.externalElementId = externalElementId;
    }
}
