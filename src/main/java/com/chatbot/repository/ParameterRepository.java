package com.chatbot.repository;

import com.chatbot.entities.IntentParameter;

import java.util.List;
import java.util.UUID;

public interface ParameterRepository {

    List<IntentParameter> getParametersForIntent(UUID intentId);
}
