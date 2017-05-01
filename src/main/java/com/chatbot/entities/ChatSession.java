package com.chatbot.entities;

import java.sql.Timestamp;
import java.util.UUID;

public class ChatSession {

    private UUID id;

    private Timestamp startTime;

    private Timestamp endTime;

    private String userId;

    private String appId;

    private String isAlive;

    public ChatSession(UUID id,
                       Timestamp startTime,
                       Timestamp endTime,
                       String userId,
                       String appId,
                       String isAlive) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.appId = appId;
        this.isAlive = isAlive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(String isAlive) {
        this.isAlive = isAlive;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}

