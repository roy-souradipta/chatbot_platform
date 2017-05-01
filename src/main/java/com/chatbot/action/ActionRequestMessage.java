package com.chatbot.action;

import com.chatbot.entities.Action;
import com.chatbot.entities.ChatConversation;
import com.chatbot.entities.Intent;
import com.chatbot.entities.IntentParameter;

import java.util.List;

public class ActionRequestMessage {

    private Intent intent;

    private List<IntentParameter> parameters;

    private Action actionEndPointData;

    public ActionRequestMessage(Intent intent,
                                List<IntentParameter> parameters,
                                Action actionEndPointData) {
        this.intent = intent;
        this.parameters = parameters;
        this.actionEndPointData = actionEndPointData;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public List<IntentParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<IntentParameter> parameters) {
        this.parameters = parameters;
    }

    public Action getActionEndPointData() {
        return actionEndPointData;
    }

    public void setActionEndPointData(Action actionEndPointData) {
        this.actionEndPointData = actionEndPointData;
    }

    public static ActionRequestMessage from(Action action, ChatConversation currentConversation) {
        return new ActionRequestMessage(currentConversation.getIntents().get(0),
                currentConversation.getIntentParameters(),
                action);
    }
}
