package com.example.bankapplication.Repositories;

import com.example.bankapplication.Models.Blik;
import com.example.bankapplication.Models.Session;
import com.example.bankapplication.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class BlikRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public String makeBlik(String token) {
        var session = getSessionByToken(token);
        if (session == null)
            return "error";

        String blikcode;
        do {
            blikcode = generateBlik();
        } while (getBlik(blikcode) != null);

        jdbcTemplate.update("insert into blikcodes(userId, blik) values (?,?)",
                session.getUser_id(),
                blikcode,
                false
        );
        return blikcode;
    }

    private String generateBlik() {
        StringBuilder blik = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            blik.append((int) (Math.random() * 10));
        }
        return blik.toString();
    }

    public int useBlik(int amount, String code, String token) {
        var session = getSessionByToken(token);
        if (session == null)
            return 0;

        var blik = getBlik(code);
        if (blik == null || blik.getTargetId() != null)
            return 0;

        if(getUser(blik.getUserId()).getBalance() < amount || amount < 0)
            return 0;

        return jdbcTemplate.update("update blikcodes SET targetId=?, amount=? WHERE blik=?", session.getUser_id(), amount, code);
    }

    private Blik getBlik(String code)  {
        try {
            return jdbcTemplate.queryForObject("select * from blikcodes where blik = ?", BeanPropertyRowMapper.newInstance(Blik.class), code);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Session getSessionByToken(String token) {
        return jdbcTemplate.queryForObject("select * from sessions where token = ?", BeanPropertyRowMapper.newInstance(Session.class), token);
    }

    private User getUser(int userId) {
        return jdbcTemplate.queryForObject("select * from users where id = ?", BeanPropertyRowMapper.newInstance(User.class), userId);
    }

    public int confirmBlik(String code, String token) {
        var session = getSessionByToken(token);
        if(session == null) return 0;

        var blik= getBlik(code);
        if(blik == null || blik.getUserId() != session.getUser_id() || blik.getTargetId() == -1) return 0;

        var res = 0;
        res += jdbcTemplate.update("update users SET balance = balance + ? WHERE id = ?", blik.getAmount(), blik.getTargetId());
        res += jdbcTemplate.update("update users SET balance = balance - ? WHERE id = ?", blik.getAmount(), blik.getUserId());
        res += jdbcTemplate.update("delete from blikcodes where blik = ?", code);
        return res == 3 ? 1 : 0;
    }
}
