package com.example.bankapplication.Controlers;

import com.example.bankapplication.Models.User;
import com.example.bankapplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public String loginUser(@RequestBody String credentials) {
        var username = credentials.split("&")[0].substring(9);
        var password = credentials.split("&")[1].substring(9);
        return userRepository.login(username, password);
    }

    @PostMapping("/add")
    public int addUser(@RequestBody User user) {
        return userRepository.addUser(user);
    }

    @PostMapping("/balance")
    public int updateBalance(@RequestBody User user, int amount) {
        return userRepository.updateBalance(user, amount);
    }

    @DeleteMapping("/delete")
    public int deleteUserById(@RequestBody int id) {
        return userRepository.deleteUserById(id);
    }
}
