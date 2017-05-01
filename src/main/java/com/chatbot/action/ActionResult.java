package com.chatbot.action;

public class ActionResult {

    private ActionStatus actionStatus;

    private String responseToClient;

     private String responseFromAction;

    public ActionResult(ActionStatus actionStatus, String responseToClient, String responseFromAction) {
        this.actionStatus = actionStatus;
        this.responseToClient = responseToClient;
        this.responseFromAction = responseFromAction;
    }

    public ActionStatus getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getResponseToClient() {
        return responseToClient;
    }

    public void setResponseToClient(String responseToClient) {
        this.responseToClient = responseToClient;
    }

    public String getResponseFromAction() {
        return responseFromAction;
    }

    public void setResponseFromAction(String responseFromAction) {
        this.responseFromAction = responseFromAction;
    }
}
