package com.chatbot.repository;

import com.chatbot.entities.IntentParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class ParameterRepositoryImpl implements ParameterRepository{

    private JdbcTemplate jdbcTemplate;

    private ParameterMapper parameterMapper;

    private static final String getParametersForIntentSql = "select id, name, type, value, sequence,"
            + " prompt_text, hint_text, validation_set, is_mandatory "
            + " from \"INTENT_PARAMETER\" "
            + " where intent_id = ?";

    @Autowired
    public ParameterRepositoryImpl(DataSource dataSource, ParameterMapper parameterMapper){
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.parameterMapper = parameterMapper;
    }

    @Override
    public List<IntentParameter> getParametersForIntent(UUID intentId) {
        return jdbcTemplate.query(getParametersForIntentSql,  new Object[]{intentId}, parameterMapper);
    }
}
