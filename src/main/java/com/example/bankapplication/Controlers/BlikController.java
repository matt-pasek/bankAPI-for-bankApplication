package com.example.bankapplication.Controlers;

import com.example.bankapplication.Repositories.BlikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blik")
public class BlikController {
    @Autowired
    BlikRepository blikRepository;

    @PostMapping("/generate")
    public String generateBlikCode(@RequestBody String data) {
        var userId = data.split("&")[0].substring(7);
        var token = data.split("&")[1].substring(6);
        return blikRepository.makeBlik(Integer.parseInt(userId), token);
    }

}
