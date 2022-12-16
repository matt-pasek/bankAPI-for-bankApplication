package com.example.bankapplication.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Blik {
    private Integer id;
    private String blik;
    private int userId;
    private boolean isUsed;

    // on create blik generate code
    // constructor
    public Blik(int userId) {
        this.userId = userId;
        this.blik = generateBlik();
        this.isUsed = false;
    }

    private String generateBlik() {
        StringBuilder blik = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            blik.append((int) (Math.random() * 10));
        }
        return blik.toString();
    }
}
