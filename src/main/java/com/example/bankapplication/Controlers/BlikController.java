package com.example.bankapplication.Controlers;

import com.example.bankapplication.Models.User;
import com.example.bankapplication.Repositories.BlikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blik")
public class BlikController {
    @Autowired
    BlikRepository blikRepository;

    @GetMapping("genereate")
    public String generateBlikCode(@RequestBody User user) {
        return blikRepository.makeBlik(user);
    }

    @GetMapping("expire")
    public int deleteBlikCode(@RequestBody User user) {
        return blikRepository.expireBlik(user);
    }

    @GetMapping("validate")
    public int validateBlikCode(@RequestBody String code, User user) {
        return blikRepository.validateBlik(code, user);
    }
}
