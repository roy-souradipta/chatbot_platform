package com.chatbot.state;

import com.chatbot.state.Events;
import com.chatbot.state.States;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class StateTransitions {

    public static final HashMap<String, String> stateMap = new HashMap<>();

    public StateTransitions(){
        stateMap.put(Events.MULTIPLE_INTENTS_FROM_NLP.toString().concat(States.PRIMITIVE.toString()),
                States.MULTIPLE_INTENTS_IDENTIFIED.toString());
        stateMap.put(Events.INTENT_IDENTIFIED_AND_PARAMETERS_REQUIRED.toString().concat(States.PRIMITIVE.toString()),
                States.INTENT_IDENTIFIED.toString());
        stateMap.put(Events.READY_FOR_ACTION.toString().concat(States.PRIMITIVE.toString()),
                States.PARAMETERS_FULFILLED.toString());
        stateMap.put(Events.SIMPLE_INTENT_WITH_NO_ACTION.toString().concat(States.PRIMITIVE.toString()),
                States.COMPLETE.toString());
        stateMap.put(Events.SIMPLE_INTENT_WITH_NO_PARAMETERS_OR_ACTION.toString().concat(States.PRIMITIVE.toString()),
                States.COMPLETE.toString());
        stateMap.put(Events.MULTIPLE_INTENTS_FROM_NLP.toString().concat(States.MULTIPLE_INTENTS_IDENTIFIED.toString()),
                States.MULTIPLE_INTENTS_IDENTIFIED.toString());
        stateMap.put(Events.INTENT_IDENTIFIED_AND_PARAMETERS_REQUIRED.toString().concat(States.MULTIPLE_INTENTS_IDENTIFIED.toString()),
                States.INTENT_IDENTIFIED.toString());
        stateMap.put(Events.READY_FOR_ACTION.toString().concat(States.MULTIPLE_INTENTS_IDENTIFIED.toString()),
                States.PARAMETERS_FULFILLED.toString());
        stateMap.put(Events.SIMPLE_INTENT_WITH_NO_ACTION.toString().concat(States.MULTIPLE_INTENTS_IDENTIFIED.toString()),
                States.COMPLETE.toString());
        stateMap.put(Events.SIMPLE_INTENT_WITH_NO_PARAMETERS_OR_ACTION.toString().concat(States.MULTIPLE_INTENTS_IDENTIFIED.toString()),
                States.COMPLETE.toString());
        stateMap.put(Events.INTENT_IDENTIFIED_AND_PARAMETERS_REQUIRED.toString().concat(States.INTENT_IDENTIFIED.toString()),
                States.INTENT_IDENTIFIED.toString());
        stateMap.put(Events.READY_FOR_ACTION.toString().concat(States.INTENT_IDENTIFIED.toString()),
                States.PARAMETERS_FULFILLED.toString());
        stateMap.put(Events.SIMPLE_INTENT_WITH_NO_PARAMETERS_OR_ACTION.toString().concat(States.INTENT_IDENTIFIED.toString()),
                States.COMPLETE.toString());
        stateMap.put(Events.SIMPLE_INTENT_WITH_NO_ACTION.toString().concat(States.INTENT_IDENTIFIED.toString()),
                States.COMPLETE.toString());
        stateMap.put(Events.ACTION_EXECUTED_SUCCESSFULLY.toString().concat(States.PARAMETERS_FULFILLED.toString()),
                States.ACTION_SUCCESS.toString());
        stateMap.put(Events.ACTION_FAILED.toString().concat(States.PARAMETERS_FULFILLED.toString()),
                States.ACTION_FAILED.toString());
        stateMap.put(Events.RESPONSE_UPDATED.toString().concat(States.ACTION_FAILED.toString()),
                States.COMPLETE.toString());
        stateMap.put(Events.RESPONSE_UPDATED.toString().concat(States.ACTION_SUCCESS.toString()),
                States.COMPLETE.toString());

    }

    public String getNextState(String currentStateName,
                              String currentEventName) {
        return stateMap.get(currentStateName.concat(currentEventName));
    }

}
