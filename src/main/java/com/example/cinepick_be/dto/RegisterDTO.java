package com.example.cinepick_be.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
   @NotBlank
   private String userId;
   @NotBlank
   private String password;
   @NotBlank
   private String confirmPassword;
   public boolean isPasswordConfirmed() {
      return password.equals(confirmPassword);
   }
}
