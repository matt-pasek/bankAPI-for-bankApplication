package com.example.bankapplication.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class History {
    private int id;
    private int user_id;
    private double total;
}
