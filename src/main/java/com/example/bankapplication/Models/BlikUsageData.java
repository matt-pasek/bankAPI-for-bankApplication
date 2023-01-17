package com.example.bankapplication.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlikUsageData {
    private int amount;
    private String code;
    private String token;
}
