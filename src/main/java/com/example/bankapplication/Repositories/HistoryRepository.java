package com.example.bankapplication.Repositories;

import com.example.bankapplication.Models.History;
import com.example.bankapplication.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<History> getUserHistory(User user) {
        return jdbcTemplate.query("select * from history", BeanPropertyRowMapper.newInstance(History.class));
    }
}
