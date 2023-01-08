package com.example.bankapplication.Repositories;

import com.example.bankapplication.Models.Blik;
import com.example.bankapplication.Models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BlikRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public String makeBlik(int userId, String token) {
        Blik blik = new Blik(userId);

        var session = jdbcTemplate.query("select * from sessions where token = ?", BeanPropertyRowMapper.newInstance(Session.class), token);
        if (session.get(0).getUser_id() != userId)
            return "error";

        jdbcTemplate.update("insert into blikcodes(userId, blik) values (?,?)",
                blik.getUserId(),
                blik.getBlik(),
                false
        );
        return blik.getBlik();
    }

    // TODO add targetId field in blikcodes to know to whom give money to
    public int useBlik(int targetId, String code) {
        if (jdbcTemplate.queryForObject("select count(*) from blik where blik = ?", Integer.class, code) == 0)
            return 0;

        return jdbcTemplate.update("update blikcodes SET isUsed = true WHERE blik=?", code);
    }

    public int confirmBlik(String code, String token) {
        var blikObj = jdbcTemplate.queryForObject("select * from blikcodes where blik=?", Blik.class, code);

        if(jdbcTemplate.queryForObject("select * from sessions where blik=?", Session.class, code).getUser_id() == blikObj.getUserId())
            return jdbcTemplate.update("delete from blikcodes where blik=?", code) == 1 && jdbcTemplate.update("update users set balance=balance-100 where id=?", blikObj.getUserId()) == 1 ? 1 : 0;
        else
            return -1;
    }
}
