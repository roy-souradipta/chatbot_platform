package com.chatbot.repository;

import com.chatbot.entities.ChatSession;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

@Component
public class ChatSessionMapper implements RowMapper<ChatSession> {

    @Override
    public ChatSession mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ChatSession((UUID) resultSet.getObject("id"),
                (Timestamp) resultSet.getObject("start_time"),
                (Timestamp) resultSet.getObject("end_time"),
                resultSet.getString("user_id"),
                resultSet.getString("app_id"),
                resultSet.getString("is_active"));
    }
}
