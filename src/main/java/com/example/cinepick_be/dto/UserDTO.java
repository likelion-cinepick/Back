package com.example.cinepick_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
   private String userId;
   private String password;
   private String nickname;
   private String mbti;
   private List<String> mood;
}
