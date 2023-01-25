package com.example.bankapplication;

import com.example.bankapplication.Models.User;
import com.example.bankapplication.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTests {
    private final String username = "Test";
    private final String password = "testpassword";
    @Autowired
    UserRepository userRepository;

    @Test
    void createUser() {
        final User userToAdd = new User();
        userToAdd.setUsername(username);
        userToAdd.setPassword(password);
        userToAdd.setBalance(1000);
        assert userRepository.addUser(userToAdd) == 1;
    }

    @Test
    void deleteUser() {
        var user = userRepository.getUserByData(username, password);
        assert  userRepository.deleteUserById(user.getId()) == 1;
    }
}
