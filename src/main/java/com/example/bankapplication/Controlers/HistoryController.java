package com.example.bankapplication.Controlers;

import com.example.bankapplication.Models.History;
import com.example.bankapplication.Models.User;
import com.example.bankapplication.Repositories.HistoryRepository;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    @Autowired
    HistoryRepository historyRepository;

    @GetMapping("")
    public List<History> getHistory(@RequestBody User user) {
        return historyRepository.getUserHistory(user);
    }
}
