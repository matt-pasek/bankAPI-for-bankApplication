package com.example.bankapplication.Repositories;

import com.example.bankapplication.Models.Session;
import com.example.bankapplication.Models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final Random random = new Random();
    public int addUser(User user) {
        return jdbcTemplate.update("insert into users(username,password,balance) values (?,?,1000)",
                user.getUsername(),
                user.getPassword()
        );
    }

    public int deleteUserById(int id){
        return jdbcTemplate.update("delete from users where id = ?", id);
    }

    public int updateBalance(User user, int amount) {
        if (user.getBalance() + amount < 0) {
            return -1;
        }
        var newBalance = user.getBalance() + amount;
        return jdbcTemplate.update("Update users set balance=? where id=?",
                newBalance,
                user.getId()
        );
    }

    public int getBalance(String username, String token) {
        var session = getSessionByToken(token);
        if (session == null)
            return -1;

        var user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE username=?", BeanPropertyRowMapper.newInstance(User.class), username);
        if(user == null) return -1;
        return user.getBalance();
    }

    private Session getSessionByToken(String token) {
        return jdbcTemplate.queryForObject("select * from sessions where token = ?", BeanPropertyRowMapper.newInstance(Session.class), token);
    }

    public String login(String username, String password) {
        var token = sha256(random.nextInt() + "hello java");
        try {
            var usr = getUserByData(username, password);
            jdbcTemplate.update("DELETE FROM sessions WHERE user_id = ?", usr.getId());
            var res = jdbcTemplate.update("INSERT INTO sessions (token, user_id) VALUES (?, ?)", token, usr.getId());
            return res == 1 ? token : "";
        } catch (EmptyResultDataAccessException e) {
            return "No user";
        }
    }

    public User getUserByData(String username, String password) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username=? AND password=?", BeanPropertyRowMapper.newInstance(User.class), username, password);
    }

    private static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
