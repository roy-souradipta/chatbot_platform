package com.chatbot.repository;

import com.chatbot.entities.Action;

import java.util.UUID;

public interface ActionRepository {

    Action getActionForIntent(UUID intentId);

    UUID getActionIdForIntent(UUID intentId);
}
