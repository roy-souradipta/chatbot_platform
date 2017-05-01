package com.chatbot.service.nlp;

import com.chatbot.state.Events;
import com.chatbot.state.StateActions;
import com.chatbot.state.StateTransitions;
import com.chatbot.state.States;
import com.chatbot.dtos.NLPResponseDto;
import com.chatbot.entities.ChatConversation;
import com.chatbot.entities.Intent;
import com.chatbot.entities.PersistentMessage;
import com.chatbot.entities.ProcessingStatus;
import com.chatbot.repository.ActionRepository;
import com.chatbot.repository.ChatConversationRepository;
import com.chatbot.repository.ChatMessageRepository;
import com.chatbot.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NLPMessageHandler {

    @Autowired
    private StateActions stateActions;

    @Autowired
    private StateTransitions stateTransitions;

    @Autowired
    private ChatConversationRepository chatConversationRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    public void handleMessage(NLPResponseDto nlpResponseDto) {
        States currentState;
        ChatConversation currentConversation = null;
        if (messagePartOfAnExistingConversation(nlpResponseDto)) {
            currentConversation =
                    chatConversationRepository.getConversation(
                            UUID.fromString(nlpResponseDto.getCurrentConversationId()));
            currentState = States.valueOf(currentConversation.getConversationState());
        } else {
            currentState = States.PRIMITIVE;
        }
        PersistentMessage currentMessage = chatMessageRepository.getChatMessage(
                UUID.fromString(nlpResponseDto.getChatMessageId()));
        Events nlpEvent =  identifyNlpEvent(nlpResponseDto);
        handleNlpEvent(nlpEvent, currentState, currentConversation, nlpResponseDto, currentMessage);
    }

    private void handleNlpEvent(Events nlpEvent,
                                States currentState,
                                ChatConversation currentConversation,
                                NLPResponseDto nlpResponseDto,
                                PersistentMessage currentMessage) {
        String nextState = stateTransitions.getNextState(nlpEvent.toString(), currentState.toString());
        if (currentConversation != null) {
            currentConversation = updateConversation(currentConversation, nlpResponseDto, nextState);
        } else if (nlpResponseDto.getIntents().length > 0) {
            currentConversation = createNewConversation(nlpResponseDto, nextState);
        }
        updatePersistentMessage(currentMessage,
                nlpResponseDto,
                currentConversation);
        if (currentConversation != null) {
            stateActions.handleStateAction(currentMessage, currentConversation);
        }
        chatMessageRepository.updateProcessingStatus(currentMessage, ProcessingStatus.COMPLETE);
    }

    private ChatConversation createNewConversation(NLPResponseDto nlpResponseDto, String nextState) {
        List<Intent> intents = new ArrayList<>();
        if(nlpResponseDto.getIntents().length >0){
            intents = Arrays.asList(nlpResponseDto.getIntents());
        }
        UUID conversationId = UUID.randomUUID();
        ChatConversation currentConversation = new ChatConversation(conversationId,
                nlpResponseDto.getDomainId(),
                nextState,
                intents,
                nlpResponseDto.getParameters());
        chatConversationRepository.saveConversation(currentConversation);
        return currentConversation;
    }

    private ChatConversation updateConversation(ChatConversation currentConversation,
                                                NLPResponseDto nlpResponseDto,
                                                String state) {
        currentConversation.setConversationState(state);
        currentConversation.setDomainId(nlpResponseDto.getDomainId());
        currentConversation.setIdentifiedIntent(Arrays.asList(nlpResponseDto.getIntents()));
        currentConversation.setIntentParameters(nlpResponseDto.getParameters());
        chatConversationRepository.updateConversation(currentConversation);
        return currentConversation;
    }

    private PersistentMessage updatePersistentMessage(PersistentMessage currentMessage,
                                                      NLPResponseDto nlpResponseDto,
                                                      ChatConversation currentConversation) {
        currentMessage.setAppId(nlpResponseDto.getAppId());
        currentMessage.setConversationId(currentConversation.getId());
        currentMessage.setResponseString(nlpResponseDto.getResponseText());
        chatMessageRepository.updateMessage(currentMessage);
        return currentMessage;
    }

    private Events identifyNlpEvent(NLPResponseDto nlpResponseDto) {
        Events nlpEvent = null;
        if (isMultipleIntentsFound(nlpResponseDto)) {
            nlpEvent = Events.MULTIPLE_INTENTS_FROM_NLP;
        } else if (isSingleIntentFound(nlpResponseDto)) {
            boolean intentRequiresParameters =
                    checkIfIntentRequiresParameters(nlpResponseDto.getIntents()[0].getIntentId());
            boolean intentHasAction =
                    checkIfIntentHasAction(nlpResponseDto.getIntents()[0].getIntentId());
            if (!intentRequiresParameters && !intentHasAction) {
                nlpEvent = Events.SIMPLE_INTENT_WITH_NO_PARAMETERS_OR_ACTION;
            } else if (nlpResponseDto.getParameterFulFillmentIndicator().equals("true")) {
                if (intentHasAction) {
                    nlpEvent = Events.READY_FOR_ACTION;
                } else {
                    nlpEvent = Events.SIMPLE_INTENT_WITH_NO_ACTION;
                }
            } else {
                nlpEvent = Events.INTENT_IDENTIFIED_AND_PARAMETERS_REQUIRED;
            }
        }
        return nlpEvent;

    }

    private boolean isSingleIntentFound(NLPResponseDto nlpResponseDto) {
        return nlpResponseDto.getIntents() != null && nlpResponseDto.getIntents().length == 1;
    }

    private boolean isMultipleIntentsFound(NLPResponseDto nlpResponseDto) {
        return nlpResponseDto.getIntents() != null &&
                nlpResponseDto.getIntents().length > 1;
    }

    private boolean checkIfIntentRequiresParameters(UUID intentId) {
        return parameterRepository.getParametersForIntent(intentId).size()> 0;
    }

    private boolean checkIfIntentHasAction(UUID intentId) {
        return actionRepository.getActionIdForIntent(intentId) != null;
    }

    private boolean messagePartOfAnExistingConversation(NLPResponseDto nlpResponseDto) {
        return Optional.ofNullable(nlpResponseDto.getCurrentConversationId()).isPresent();
    }
}
