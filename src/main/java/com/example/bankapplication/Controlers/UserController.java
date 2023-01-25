package com.example.bankapplication.Controlers;

import com.example.bankapplication.Models.LoginCredentials;
import com.example.bankapplication.Models.Token;
import com.example.bankapplication.Repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginCredentials loginCredentials) {
        return userRepository.login(loginCredentials.getUsername(), loginCredentials.getPassword());
    }

    @GetMapping("/balance")
    public int getBalance(@RequestParam String username, @RequestHeader Token token) {
        return userRepository.getBalance(username, token.getToken());
    }
}
