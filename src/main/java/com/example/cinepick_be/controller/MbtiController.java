package com.example.cinepick_be.controller;

import com.example.cinepick_be.dto.MbtiDTO;
import com.example.cinepick_be.entity.Mbti;
import com.example.cinepick_be.entity.User;
import com.example.cinepick_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cinepick/test")
@RequiredArgsConstructor
public class MbtiController {

   private final UserService userService;

   @PostMapping("/result")
   public ResponseEntity<User> mbtiResult(@RequestBody MbtiDTO mbtiDTO){
      if(mbtiDTO.getMbti() == null || mbtiDTO.getMbti().size() !=4)
         return ResponseEntity.badRequest().build();

      String result = String.join("", mbtiDTO.getMbti());

      User user= userService.updateUserWithMbti(mbtiDTO.getUserId(),result);

      return ResponseEntity.ok(user);

   }

   @GetMapping("/result/{userId}")
   public ResponseEntity<Mbti> getMbtiResult(@PathVariable String userId) {
      Mbti mbti = userService.getMbtiByUserId(userId);
      return ResponseEntity.ok(mbti);
   }
}
