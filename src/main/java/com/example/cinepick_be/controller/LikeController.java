package com.example.cinepick_be.controller;

import com.example.cinepick_be.dto.LikeDTO;
import com.example.cinepick_be.service.LikeService;
import com.example.cinepick_be.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/my/like")
@RequiredArgsConstructor
public class LikeController {
   private final LikeService likeService;
   @GetMapping()
   public ResponseEntity<List<LikeDTO>> getLikes(Authentication authentication){
      String userId= authentication.getName();
      List<LikeDTO> likes =  likeService.like(userId);
      return ResponseEntity.ok(likes);
   }

   @DeleteMapping("/delete")
   public ResponseEntity<Void> deleteLike(
      @PathVariable String userId,
      @PathVariable Long movieId){
      likeService.addOrRemoveLike(userId, movieId);
      return ResponseEntity.noContent().build();
   }

}
