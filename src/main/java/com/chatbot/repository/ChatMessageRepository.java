package com.chatbot.repository;

import com.chatbot.entities.PersistentMessage;
import com.chatbot.entities.ProcessingStatus;

import java.util.Optional;
import java.util.UUID;

public interface ChatMessageRepository {

    public PersistentMessage getChatMessage(UUID messageId);

    void saveMessage(PersistentMessage persistentMessage);

    int updateMessage(PersistentMessage currentMessage);

    int updateProcessingStatus(PersistentMessage currentMessage, ProcessingStatus complete);
}
