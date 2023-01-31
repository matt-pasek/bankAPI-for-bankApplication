package com.example.bankapplication.Controlers;

import com.example.bankapplication.Models.BlikConfirmData;
import com.example.bankapplication.Models.BlikUsageData;
import com.example.bankapplication.Models.Token;
import com.example.bankapplication.Repositories.BlikRepository;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/confirm")
    public int canConfirmBlikCode(@RequestParam("blik") String blik) {
        return blikRepository.canConfirmBlik(blik);
    }

    @PostMapping("/confirm")
    public int confirmBlikCode(@RequestBody BlikConfirmData blikConfirmData) {

        return blikRepository.confirmBlik(blikConfirmData.getCode(), blikConfirmData.getToken());
    }
}
