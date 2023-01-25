package com.example.bankapplication.Controlers;

import com.example.bankapplication.Models.BlikConfirmData;
import com.example.bankapplication.Models.BlikUsageData;
import com.example.bankapplication.Models.Token;
import com.example.bankapplication.Repositories.BlikRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blik")
public class BlikController {
    private final BlikRepository blikRepository;

    public BlikController(BlikRepository blikRepository) {
        this.blikRepository = blikRepository;
    }

    @PostMapping("/generate")
    public String generateBlikCode(@RequestBody Token token) {
        return blikRepository.makeBlik(token.getToken());
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
