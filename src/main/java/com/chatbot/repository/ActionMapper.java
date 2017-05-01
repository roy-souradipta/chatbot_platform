package com.chatbot.repository;

import com.chatbot.entities.Action;
import com.chatbot.entities.PersistentMessage;
import com.chatbot.entities.ProcessingStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

@Component
public class ActionMapper implements RowMapper<Action> {

    @Override
    public Action mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Action((UUID) resultSet.getObject("action_id"),
                resultSet.getString("action_name"),
                resultSet.getString("action_type"),
                resultSet.getString("action_subtype"),
                resultSet.getString("action_response"),
                resultSet.getString("default_fallback_msg"),
                resultSet.getString("endpoint"));
    }
}
