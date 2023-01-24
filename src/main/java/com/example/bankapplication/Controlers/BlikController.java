package com.example.bankapplication.Controlers;

import com.example.bankapplication.Models.BlikConfirmData;
import com.example.bankapplication.Models.BlikUsageData;
import com.example.bankapplication.Models.TokenData;
import com.example.bankapplication.Repositories.BlikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blik")
public class BlikController {
    @Autowired
    BlikRepository blikRepository;

    @PostMapping("/generate")
    public String generateBlikCode(@RequestBody TokenData tokenData) {
        return blikRepository.makeBlik(tokenData.getToken());
    }

    @PostMapping("/use")
    public int useBlikCode(@RequestBody BlikUsageData blikUsageData) {

        return blikRepository.useBlik(blikUsageData.getAmount(), blikUsageData.getCode(), blikUsageData.getToken());
    }

    @PostMapping("/confirm")
    public int confirmBlikCode(@RequestBody BlikConfirmData blikConfirmData) {

        return blikRepository.confirmBlik(blikConfirmData.getCode(), blikConfirmData.getToken());
    }
}
