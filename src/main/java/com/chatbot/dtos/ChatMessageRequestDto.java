package com.chatbot.dtos;

import com.chatbot.entities.PersistentMessage;
import com.chatbot.entities.ProcessingStatus;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class ChatMessageRequestDto {

    private String chatRequestContent;

    private String conversationId;

    private String sessionId;

    private String userId;

    private String botAppId;

    public String getChatRequestContent() {
        return chatRequestContent;
    }

    public void setChatRequestContent(String chatRequestContent) {
        this.chatRequestContent = chatRequestContent;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBotAppId() {
        return botAppId;
    }

    public void setBotAppId(String botAppId) {
        this.botAppId = botAppId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public PersistentMessage toPersistentMessage(Instant currentInstant, UUID messageId) {
        return new PersistentMessage(messageId, null,
                UUID.fromString(this.sessionId),
                this.botAppId,
                this.chatRequestContent,
                null,
                ProcessingStatus.IN_PROGRESS,
                Timestamp.from(currentInstant));
    }
}
