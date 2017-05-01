package com.chatbot.repository;

import com.chatbot.entities.PersistentMessage;
import com.chatbot.entities.ProcessingStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

@Component
public class ChatMessageMapper implements RowMapper<PersistentMessage> {

    @Override
    public PersistentMessage mapRow(ResultSet resultSet, int i) throws SQLException {
        return new PersistentMessage((UUID) resultSet.getObject("message_id"),
                (UUID) resultSet.getObject("conversation_id"),
                (UUID) resultSet.getObject("session_id"),
                resultSet.getString("app_id"),
                resultSet.getString("chat_string"),
                resultSet.getString("response_string"),
                ProcessingStatus.valueOf(resultSet.getString("processing_status")),
                (Timestamp) resultSet.getObject("query_received_time"));
    }
}
