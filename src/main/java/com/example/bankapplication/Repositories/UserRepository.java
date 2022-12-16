package com.example.bankapplication.Repositories;

import com.example.bankapplication.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public User loginUser(String username, String password) {
        return jdbcTemplate.queryForObject("select * from users where username = ? and password = ?", User.class, username, password);
    }

    public int addUser(User user) {
        jdbcTemplate.update("insert into users(username,password,balance) values (?,?,0)",
                user.getUsername(),
                user.getPassword()
        );
        return 0;
    }

    public int deleteUserById(int id){
        return jdbcTemplate.update("delete from users where id = ?", id);
    }

    public int updateBalance(User user, int amount) {
        if (user.getBalance() + amount < 0) {
            return -1;
        }
        var newBalance = user.getBalance() + amount;
        jdbcTemplate.update("Update users set balance=? where id=?",
                newBalance,
                user.getId()
        );
        if (amount < 0) {
            jdbcTemplate.update("insert into history(user_id, total) values (?,?)",
                    user.getId(),
                    -amount
            );
        }
        return 0;
    }
}
