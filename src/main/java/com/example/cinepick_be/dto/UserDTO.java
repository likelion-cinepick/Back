package com.example.cinepick_be.dto;

import com.example.cinepick_be.entity.User;
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
   private String profileUrl;
   private List<String> mood;

   public UserDTO(User user) {
      this.userId = user.getUserId();
      this.mbti = user.getMbti().getMbti();
   }
}