package com.chatbot.state;

public enum Events {

    MULTIPLE_INTENTS_FROM_NLP,
    READY_FOR_ACTION,
    INTENT_IDENTIFIED_AND_PARAMETERS_REQUIRED,
    SIMPLE_INTENT_WITH_NO_PARAMETERS_OR_ACTION,
    ACTION_EXECUTED_SUCCESSFULLY,
    ACTION_FAILED,
    SIMPLE_INTENT_WITH_NO_ACTION,
    RESPONSE_UPDATED

}
