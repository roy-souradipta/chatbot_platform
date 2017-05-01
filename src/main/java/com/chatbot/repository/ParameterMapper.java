package com.chatbot.repository;

import com.chatbot.entities.IntentParameter;
import com.chatbot.entities.ValidationSet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

@Component
public class ParameterMapper implements RowMapper<IntentParameter> {

    private static final Logger logger = Logger.getLogger(ParameterMapper.class);

    @Override
    public IntentParameter mapRow(ResultSet resultSet, int i) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        ValidationSet validationSet = null;
        try {
            if(resultSet.getString("validation_set") != null)
            validationSet = objectMapper.readValue(resultSet.getString("validation_set"), ValidationSet.class);
        } catch (IOException e) {
           logger.error("Error in parsing Json", e);
        }
        return new IntentParameter((UUID) resultSet.getObject("id"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getString("value"),
                    resultSet.getInt("sequence"),
                    resultSet.getString("prompt_text"),
                    resultSet.getString("hint_text"),
                    validationSet,
                    resultSet.getBoolean("is_mandatory"));
    }
}
