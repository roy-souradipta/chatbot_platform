package com.chatbot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitconfig")
public class RabbitConfigurationProperties {

    private String hostname;

    private String username;

    private String password;

    private String exchange;

    private String topics;

    private String nlpsenderroutingkey;

    private String nlpreceiverroutingkey;


    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getNlpsenderroutingkey() {
        return nlpsenderroutingkey;
    }

    public void setNlpsenderroutingkey(String nlpsenderroutingkey) {
        this.nlpsenderroutingkey = nlpsenderroutingkey;
    }

    public String getNlpreceiverroutingkey() {
        return nlpreceiverroutingkey;
    }

    public void setNlpreceiverroutingkey(String nlpreceiverroutingkey) {
        this.nlpreceiverroutingkey = nlpreceiverroutingkey;
    }
}
