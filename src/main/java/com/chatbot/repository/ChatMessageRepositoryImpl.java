package com.chatbot.repository;

import com.chatbot.entities.PersistentMessage;
import com.chatbot.entities.ProcessingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ChatMessageRepositoryImpl implements ChatMessageRepository{

    private JdbcTemplate jdbcTemplate;

    private ChatMessageMapper chatMessageMapper;

    private static final String getMessageSql = "select message_id, conversation_id, session_id, app_id, chat_string,"
            + " response_string, processing_status, query_received_time from \"CHAT_MESSAGE\" "
            + " where message_id = ?";

    private static final String insertMessageSql = "insert into \"CHAT_MESSAGE\" ("
            + "message_id, conversation_id, session_id, app_id, chat_string, response_string, processing_status, query_received_time)"
            + " values(?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String updateMessageSql = "update \"CHAT_MESSAGE\" set conversation_id = ?,"
            + "session_id = ?, app_id = ?, response_string = ?, processing_status = ? where message_id = ?";

    private static final String updateProcessingStatusSql = "update \"CHAT_MESSAGE\" set processing_status = ?"
            + " where message_id = ?";

    @Autowired
    public ChatMessageRepositoryImpl(DataSource dataSource, ChatMessageMapper chatMessageMapper){
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.chatMessageMapper = chatMessageMapper;
    }

    @Override
    public PersistentMessage getChatMessage(UUID messageId) {
        return jdbcTemplate.queryForObject(
                getMessageSql, new Object[]{messageId}, chatMessageMapper);
    }

    @Override
    public void saveMessage(PersistentMessage persistentMessage) {
        jdbcTemplate.update(insertMessageSql, new Object[]{persistentMessage.getMessageId(),
                persistentMessage.getConversationId(),persistentMessage.getSessionId(),
                 persistentMessage.getAppId(), persistentMessage.getChatString(),
                persistentMessage.getResponseString(),persistentMessage.getProcessingStatus().toString(),
                persistentMessage.getQueryReceivedTime()});
    }

    @Override
    public int updateMessage(PersistentMessage currentMessage) {
        return jdbcTemplate.update(updateMessageSql, new Object[]{currentMessage.getConversationId(),
                currentMessage.getSessionId(), currentMessage.getAppId(), currentMessage.getResponseString(),
                currentMessage.getProcessingStatus().toString(), currentMessage.getMessageId()});
    }

    @Override
    public int updateProcessingStatus(PersistentMessage currentMessage, ProcessingStatus status) {
        return jdbcTemplate.update(updateProcessingStatusSql, new Object[]{status.toString(),
                currentMessage.getMessageId()});
    }
}
