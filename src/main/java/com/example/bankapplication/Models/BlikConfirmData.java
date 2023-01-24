package com.example.bankapplication.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlikConfirmData {
    private String token;
    private String code;
}
