package com.chatbot.state;

import com.chatbot.action.ActionInvoker;
import com.chatbot.action.ActionRequestMessage;
import com.chatbot.action.ActionResult;
import com.chatbot.action.ActionStatus;
import com.chatbot.state.Events;
import com.chatbot.state.StateTransitions;
import com.chatbot.state.States;
import com.chatbot.entities.Action;
import com.chatbot.entities.ChatConversation;
import com.chatbot.entities.PersistentMessage;
import com.chatbot.repository.ActionRepository;
import com.chatbot.repository.ChatConversationRepository;
import com.chatbot.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Component
public class StateActions {

    private StateTransitions stateTransitions;

    private final Map<String, BiConsumer<PersistentMessage, ChatConversation>> stateActionMap;

    private ActionInvoker actionInvoker;

    private ActionRepository actionRepository;

    private ChatMessageRepository chatMessageRepository;

    private ChatConversationRepository chatConversationRepository;

    @Autowired
    public StateActions(ActionRepository actionRepository,
                        ChatMessageRepository chatMessageRepository,
                        ChatConversationRepository chatConversationRepository,
                        ActionInvoker actionInvoker,
                        StateTransitions stateTransitions) {
        this.stateActionMap = new HashMap<>();
        this.actionInvoker = actionInvoker;
        this.chatConversationRepository = chatConversationRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.actionRepository = actionRepository;
        this.stateTransitions = stateTransitions;
        stateActionMap.put(States.MULTIPLE_INTENTS_IDENTIFIED.toString(), this::processMultipleIntentsStateAction);
        stateActionMap.put(States.INTENT_IDENTIFIED.toString(), this::processIntentStateAction);
        stateActionMap.put(States.PARAMETERS_FULFILLED.toString(), this::processParameterFulfilledStateAction);
        stateActionMap.put(States.ACTION_SUCCESS.toString(), this::processActionSuccessStateAction);
        stateActionMap.put(States.ACTION_FAILED.toString(), this::processActionFailedStateAction);
        stateActionMap.put(States.COMPLETE.toString(), this::processCompleteStateAction);
    }

    public void handleStateAction(PersistentMessage currentMessage, ChatConversation currentConversation) {
        stateActionMap.get(currentConversation.getConversationState()).accept(currentMessage, currentConversation);
    }

    private void processMultipleIntentsStateAction(PersistentMessage currentMessage, ChatConversation currentConversation) {
        //TODO
    }

    private void processIntentStateAction(PersistentMessage currentMessage, ChatConversation currentConversation) {
        //TODO
    }

    private void processParameterFulfilledStateAction(PersistentMessage currentMessage, ChatConversation currentConversation) {
        Action action = actionRepository.getActionForIntent(currentConversation.getIntents().get(0).getIntentId());
        ActionResult actionResult = actionInvoker.handleActionRequest(
                ActionRequestMessage.from(action, currentConversation));
        //identifyAction Events
        String nextState = stateTransitions.getNextState(currentConversation.getConversationState(),
                identifyActionEvent(actionResult).toString());
        currentConversation.setConversationState(nextState);
        if (actionResult.getResponseToClient() != null)
            currentMessage.setResponseString(actionResult.getResponseToClient());
        else
            currentMessage.setResponseString(action.getDefaultFallback());
        chatConversationRepository.updateConversation(currentConversation);
        chatMessageRepository.updateMessage(currentMessage);
        handleStateAction(currentMessage, currentConversation);

    }

    private Events identifyActionEvent(ActionResult actionResult) {
        Events currentEvent = null;
        if(actionResult.getActionStatus().equals(ActionStatus.SUCCESS)){
            currentEvent = Events.ACTION_EXECUTED_SUCCESSFULLY;
        }else{
            currentEvent = Events.ACTION_FAILED;
        }
        return currentEvent;
    }

    private void processActionSuccessStateAction(PersistentMessage currentMessage, ChatConversation currentConversation) {
        String nextState = stateTransitions.getNextState(
                Events.RESPONSE_UPDATED.toString(), currentConversation.getConversationState());
        currentConversation.setConversationState(nextState);
        chatConversationRepository.updateConversation(currentConversation);
    }

    private void processActionFailedStateAction(PersistentMessage currentMessage, ChatConversation currentConversation) {
        String nextState = stateTransitions.getNextState(
                Events.RESPONSE_UPDATED.toString(), currentConversation.getConversationState());
        currentConversation.setConversationState(nextState);
        chatConversationRepository.updateConversation(currentConversation);
    }

    private void processCompleteStateAction(PersistentMessage currentMessage, ChatConversation currentConversation) {
        //TODO
    }
}
