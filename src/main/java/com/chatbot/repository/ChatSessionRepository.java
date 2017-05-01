package com.chatbot.repository;

import com.chatbot.entities.ChatSession;

import java.util.Optional;
import java.util.UUID;

public interface ChatSessionRepository {

    void saveNewSession(ChatSession newChatSession);

    ChatSession getSessionData(UUID sessionId);
}
