package com.example.cinepick_be.controller;
import com.example.cinepick_be.dto.*;
import com.example.cinepick_be.security.JwtTokenProvider;
import com.example.cinepick_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinepick")
public class UserController {
   final private UserService userService;
   final private JwtTokenProvider jwtTokenProvider;
   final private AuthenticationManager authenticationManager;

   @PostMapping("/register")
   public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
      userService.register(registerDTO);
      return ResponseEntity.ok("계정 생성");
   }
   @PostMapping("/checkId")
   public String checkUserId(@RequestParam String userId){
      boolean isAvalilable = userService.checkUserId(userId);
      if(isAvalilable)
         return "사용 가능한 아이디 입니다.";
      else return "이미 존재하는 아이디 입니다.";
   }
   @PostMapping("/nickname")
   public ResponseEntity<String> addNick(@RequestBody NickDTO nickDTO){
      userService.addNick(nickDTO);
      return ResponseEntity.ok(nickDTO.getNickname());
   }

   @PostMapping("/mbti")
   public ResponseEntity<String> addMbti(@RequestParam String userId,@RequestParam String mbti){
      userService.addMbti(userId, mbti);
      return ResponseEntity.ok(mbti);
   }

   @PostMapping("/mood")
   public ResponseEntity<List> addMood(@RequestBody MoodDTO moodDTO){
      userService.addMood(moodDTO);
      return ResponseEntity.ok(moodDTO.getMood());
   }

   @PostMapping("/login")
   public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserDTO userDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
      try {
         // 사용자 인증
         org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(userDto.getUserId(), userDto.getPassword())
         );

         // 인증 성공 시 JWT 토큰 생성
         String jwtToken = jwtTokenProvider.createToken(authentication.getName());

         // 토큰을 Map 형태로 반환
         Map<String, String> response = new HashMap<>();
         response.put("token", jwtToken);

         return ResponseEntity.ok(response);
      } catch (AuthenticationException ex) {
         throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
      }
   }
   @GetMapping("/my")
   public ResponseEntity<UserDTO> getMyPage(Authentication authentication){
      String userId=authentication.getName();
      UserDTO user = userService.MyPage(userId);
      return ResponseEntity.ok(user);
   }


}
