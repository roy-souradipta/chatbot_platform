package com.chatbot.entities;

import java.util.UUID;

public class IntentParameter {

    private UUID id;

    private String name;

    private String type;

    private String value;

    public IntentParameter() {
    }

    public IntentParameter(UUID id,
                           String name,
                           String type,
                           String value,
                           int sequence,
                           String promptText,
                           String hintText,
                           ValidationSet validationSet,
                           boolean isMandatory) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.sequence = sequence;
        this.promptText = promptText;
        this.hintText = hintText;
        this.validationSet = validationSet;
        this.isMandatory = isMandatory;
    }

    private int sequence;

    private String promptText;

    private String hintText;

    private ValidationSet validationSet;

    private boolean isMandatory;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public ValidationSet getValidationSet() {
        return validationSet;
    }

    public void setValidationSet(ValidationSet validationSet) {
        this.validationSet = validationSet;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }
}
