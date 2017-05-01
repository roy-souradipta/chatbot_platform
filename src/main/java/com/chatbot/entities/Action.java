package com.chatbot.entities;

import java.util.UUID;

public class Action {

    private UUID actionId;
    private String actionName;
    private String actionType;
    private String actionSubType;

    public Action(UUID actionId,
                  String actionName,
                  String actionType,
                  String actionSubType,
                  String actionResponse,
                  String defaultFallback,
                  String endpoint) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.actionType = actionType;
        this.actionSubType = actionSubType;
        this.actionResponse = actionResponse;
        this.defaultFallback = defaultFallback;
        this.endpoint = endpoint;
    }

    private String actionResponse;
    private String defaultFallback;
    private String endpoint;

    public UUID getActionId() {
        return actionId;
    }

    public void setActionId(UUID actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionSubType() {
        return actionSubType;
    }

    public void setActionSubType(String actionSubType) {
        this.actionSubType = actionSubType;
    }

    public String getActionResponse() {
        return actionResponse;
    }

    public void setActionResponse(String actionResponse) {
        this.actionResponse = actionResponse;
    }

    public String getDefaultFallback() {
        return defaultFallback;
    }

    public void setDefaultFallback(String defaultFallback) {
        this.defaultFallback = defaultFallback;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
