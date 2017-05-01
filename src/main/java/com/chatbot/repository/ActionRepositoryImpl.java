package com.chatbot.repository;

import com.chatbot.entities.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.swing.*;
import java.util.UUID;

@Repository
public class ActionRepositoryImpl implements ActionRepository{

    private JdbcTemplate jdbcTemplate;

    private ActionMapper actionMapper;

    private static final String getActionSql = "select action_id, action_name, action_type, action_subtype,"
            + " action_response, default_fallback_msg, endpoint from \"ACTION\" "
            + " where action_id = (select action_id from \"INTENT\" where id = ?)";

    private static final String getActionIdSql = "select action_id from \"INTENT\" where id = ?";

    @Autowired
    public ActionRepositoryImpl(DataSource dataSource, ActionMapper actionMapper){
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.actionMapper = actionMapper;
    }

    @Override
    public Action getActionForIntent(UUID intentId) {
        return jdbcTemplate.queryForObject(
                getActionSql, new Object[]{intentId}, actionMapper);
    }

    @Override
    public UUID getActionIdForIntent(UUID intentId) {
        return jdbcTemplate.queryForObject(
                getActionIdSql, new Object[]{intentId}, UUID.class);
    }
}
