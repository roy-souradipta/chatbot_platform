package com.chatbot.repository;

import com.chatbot.entities.ChatConversation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ChatConversationRepositoryImpl implements ChatConversationRepository{

    private JdbcTemplate jdbcTemplate;

    private ChatConversationMapper chatConversationMapper;

    private static final String getConversationSql = "select id, domain_id, conversation_state, intents, intent_parameters"
            + " from \"CHAT_CONVERSATION\" "
            + " where id = ?";

    private static final String insertConversationSql = "insert into \"CHAT_CONVERSATION\" ("
            + "id, domain_id, conversation_state, intents, intent_parameters)"
            + " values(?, ?, ?, ?, ?)";

    private static final String updateConversationSql = "update \"CHAT_CONVERSATION\" set domain_id = ?,"
            + "conversation_state = ?, intents = ?, intent_parameters = ? where id = ?";

    @Autowired
    public ChatConversationRepositoryImpl(DataSource dataSource,ChatConversationMapper chatConversationMapper){
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.chatConversationMapper = chatConversationMapper;
    }

    @Override
    public ChatConversation getConversation(UUID conversationId) {
        return jdbcTemplate.queryForObject(
                getConversationSql, new Object[]{conversationId}, chatConversationMapper);
    }

    @Override
    public void saveConversation(ChatConversation chatConversation) {
        PGobject intentList = new PGobject();
        PGobject intentParameterList = new PGobject();
        intentList.setType("jsonb");
        intentParameterList.setType("jsonb");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            try {
                intentList.setValue(objectMapper.writeValueAsString(chatConversation.getIntents()));
                intentParameterList.setValue(objectMapper.writeValueAsString(chatConversation.getIntentParameters()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcTemplate.update(insertConversationSql, new Object[]{chatConversation.getId(),
                    chatConversation.getDomainId(),chatConversation.getConversationState(),
                    intentList,
                intentParameterList});
    }

    @Override
    public UUID updateConversation(ChatConversation currentConversation) {
        PGobject intentList = new PGobject();
        PGobject intentParameterList = new PGobject();
        intentList.setType("jsonb");
        intentParameterList.setType("jsonb");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            try {
                intentList.setValue(objectMapper.writeValueAsString(currentConversation.getIntents()));
                intentParameterList.setValue(objectMapper.writeValueAsString(currentConversation.getIntentParameters()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcTemplate.update(updateConversationSql, new Object[]{
                    currentConversation.getDomainId(), currentConversation.getConversationState(),
                    intentList,
                    intentParameterList,
                    currentConversation.getId()});
        return currentConversation.getId();
    }
}
