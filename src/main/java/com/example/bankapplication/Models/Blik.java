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
    private Integer targetId;
    private int amount;
}
