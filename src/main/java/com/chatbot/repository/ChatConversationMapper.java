package com.chatbot.repository;

import com.chatbot.entities.ChatConversation;
import com.chatbot.entities.Intent;
import com.chatbot.entities.IntentParameter;
import com.chatbot.exception.JsonParsingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Component
public class ChatConversationMapper implements RowMapper<ChatConversation> {

    private static final Logger logger = Logger.getLogger(ChatConversationMapper.class);

    @Override
    public ChatConversation mapRow(ResultSet resultSet, int i) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        List<Intent> intents = null;
        List<IntentParameter> intentParameters = null;
        try {
            if(resultSet.getString("intents") != null)
            intents = mapper.readValue(resultSet.getString("intents"),
                    mapper.getTypeFactory().constructCollectionType(List.class, Intent.class));
            if(resultSet.getString("intent_parameters") != null)
            intentParameters = mapper.readValue(resultSet.getString("intent_parameters"),
                    mapper.getTypeFactory().constructCollectionType(List.class, IntentParameter.class));
        } catch (IOException e) {
            logger.error( "Invalid Json",e);
        }
        return new ChatConversation((UUID) resultSet.getObject("id"),
                resultSet.getString("domain_id"),
                resultSet.getString("conversation_state"),
                intents,
                intentParameters);
    }
}
