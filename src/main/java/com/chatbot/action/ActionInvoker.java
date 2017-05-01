package com.chatbot.action;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class ActionInvoker {

    private final Map<String, Function<ActionRequestMessage, ActionResult>> actionForTypeMap;

    public ActionInvoker() {
        this.actionForTypeMap = new HashMap<>();
        actionForTypeMap.put("WEBSERVICE.RESTWEBHOOK", this::processRestWebhookActionRequest);
        actionForTypeMap.put("MESSAGING.MESSAGINGHOOK", this::processMessagingHookActionRequest);
        //more actions can be added here
    }

    private ActionResult processMessagingHookActionRequest(ActionRequestMessage actionRequestMessage) {
        //TODO
        return null;
    }

    private ActionResult processRestWebhookActionRequest(ActionRequestMessage actionRequestMessage) {
        RestTemplate restTemplate = new RestTemplate();
        ActionResult actionResult = null;
        try {
            actionResult =
                    restTemplate.getForObject(actionRequestMessage.getActionEndPointData().getEndpoint(), ActionResult.class);
        } catch (RuntimeException ex) {
            actionResult = new ActionResult(ActionStatus.FAILURE, null, null);
        }
        return actionResult;
    }

    public ActionResult handleActionRequest(ActionRequestMessage actionRequestMessage) {
        return actionForTypeMap.get(actionRequestMessage.getActionEndPointData().getActionType().concat(".")
                .concat(actionRequestMessage.getActionEndPointData().getActionSubType())).apply(actionRequestMessage);
    }
}
