package com.chatbot.repository;

import com.chatbot.entities.ChatSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ChatSessionRepositoryImpl implements ChatSessionRepository{

    private JdbcTemplate jdbcTemplate;

    private ChatSessionMapper chatSessionMapper;

    private static final String getSessionSql = "select id, start_time, end_time, user_id, app_id, is_active"
            + " from \"CHAT_SESSION\" "
            + " where id = ?";

    private static final String insertSessionSql = "insert into \"CHAT_SESSION\" ("
            + "id, start_time, end_time, user_id, app_id, is_active) values(?, ?, ?, ?, ?, ?)";

    @Autowired
    public ChatSessionRepositoryImpl(DataSource dataSource, ChatSessionMapper chatSessionMapper){
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.chatSessionMapper = chatSessionMapper;
    }

    @Override
    public void saveNewSession(ChatSession newChatSession) {
        jdbcTemplate.update(insertSessionSql, new Object[]{newChatSession.getId(), newChatSession.getStartTime(),
                newChatSession.getEndTime(), newChatSession.getUserId(), newChatSession.getAppId(),
                newChatSession.getIsAlive()});
    }

    @Override
    public ChatSession getSessionData(UUID sessionId) {
        return jdbcTemplate.queryForObject(
                getSessionSql, new Object[]{sessionId}, chatSessionMapper);
    }
}
