package com.example.bankapplication.Repositories;

import com.example.bankapplication.Models.Blik;
import com.example.bankapplication.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BlikRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public String makeBlik(User user) {
        var blik = new Blik(user.getId());
        var blikExists = jdbcTemplate.queryForObject("select count(*) from blik where blik = ?", Integer.class, blik.getBlik());
        if (blikExists > 0) {
            return "error";
        }
        jdbcTemplate.update("insert into blik(user_id, blik) values (?,?)",
                blik.getUserId(),
                blik.getBlik(),
                false
        );
        return blik.getBlik();
    }

    public int expireBlik(User user) {
        jdbcTemplate.update("delete from blik where isValid = ?", 0);
        jdbcTemplate.update("delete from blik where userId = ?", user.getId());
        return 0;
    }

    public int validateBlik(String blik, User user) {
        var blikExists = jdbcTemplate.queryForObject("select count(*) from blik where blik = ? and userId = ?", Integer.class, blik, user.getId());
        if (blikExists == 0) {
            return -1;
        }
        return 0;
    }
}
