package com.example.cinepick_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MbtiResultDTO {
   private String userId;
   private List<String> mbti;
}
