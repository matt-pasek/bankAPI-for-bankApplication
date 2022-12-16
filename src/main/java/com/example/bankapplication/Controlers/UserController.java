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

    @GetMapping("")
    public User loginUser(@RequestBody String username, String password) {
        return userRepository.loginUser(username, password);
    }

    @PostMapping("add")
    public int addUser(@RequestBody User user) {
        return userRepository.addUser(user);
    }

    @PostMapping("balance")
    public int updateBalance(@RequestBody User user, int amount) {
        return userRepository.updateBalance(user, amount);
    }

    @DeleteMapping("delete")
    public int deleteUserById(@RequestBody int id) {
        return userRepository.deleteUserById(id);
    }
}
