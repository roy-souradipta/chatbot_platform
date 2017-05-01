package com.chatbot.repository;

import com.chatbot.entities.ChatConversation;
import com.chatbot.entities.ChatSession;

import java.util.Optional;
import java.util.UUID;

public interface ChatConversationRepository {

    ChatConversation getConversation(UUID conversationId);

    void saveConversation(ChatConversation chatConversation);

    UUID updateConversation(ChatConversation currentConversation);
}
