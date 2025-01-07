package com.example.cinepick_be.service;

import com.example.cinepick_be.dto.*;
import com.example.cinepick_be.entity.Mbti;
import com.example.cinepick_be.entity.User;
import com.example.cinepick_be.repository.MbtiRepository;
import com.example.cinepick_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
   private final UserRepository userRepository;
   private final MbtiRepository mbtiRepository;
   private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
   public User register(RegisterDTO user){
      if (!user.isPasswordConfirmed()) {
         throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      }
      if(userRepository.findByUserId(user.getUserId()).isPresent()){
         throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
      }

      User newUser = new User();
      newUser.setUserId(user.getUserId());
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      newUser.setPassword(encodedPassword);

      return userRepository.save(newUser);
   }
   public Boolean checkUserId(String userId){
      return !userRepository.existsByUserId(userId);
   }
   public User addNick(NickDTO nickDTO){
      User user = userRepository.findByUserId(nickDTO.getUserId())
              .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
      user.setNickname(nickDTO.getNickname());

      return userRepository.save(user);

   }

   public User addMbti(String userId, String mbtiType) {
      // 1. 사용자와 MBTI 정보를 가져옴
      User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException());
      Mbti mbti = mbtiRepository.findByMbti(mbtiType);

      if (mbti == null) {
         throw new RuntimeException("MBTI를 찾을 수 없습니다.");
      }

      user.setMbti(mbti);
      user.setProfileImageUrl(mbti.getProfileImage());


      return userRepository.save(user);
   }

   public User addMood(MoodDTO moodDTO){
      User user = userRepository.findByUserId(moodDTO.getUserId())
              .orElseThrow(()-> new IllegalArgumentException());
      user.setMoodList(moodDTO.getMood());

      return userRepository.save(user);
   }

   public UserDTO MyPage(String userId){
      User user = userRepository.findByUserId(userId)
              .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"));


      return new UserDTO(
         user.getUserId(),
         user.getPassword(),
         user.getNickname(),
         user.getMbti().getMbti(),
         user.getProfileImageUrl(),
         user.getMoodList()
      );
   }

   @Override
   public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
      User user = userRepository.findByUserId(userId)
              .orElseThrow(()->new UsernameNotFoundException("User not found with ID: "+userId));

      return org.springframework.security.core.userdetails.User
              .withUsername(user.getUserId())
              .password(user.getPassword())
              .authorities("USER")
              .build();
   }


   @Value("${file.upload-dir}")
   private String imageDir;  // MBTI 유형에 맞는 이미지를 저장한 디렉토리

   public UserDTO updateUserWithMbti(String userId, String mbtiType) {
      User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));

      Mbti mbti = mbtiRepository.findByMbti(mbtiType);

      if (mbti == null) {
         throw new RuntimeException("MBTI를 찾을 수 없습니다.");
      }

      user.setMbti(mbti);

      String profileImageFileName = mbtiType.toLowerCase() + ".png"; // 예: enfp.png
      user.setProfileImageUrl(profileImageFileName);
      userRepository.save(user);
      UserDTO userDTO= new UserDTO();
      userDTO.setUserId(user.getUserId());
      userDTO.setMbti(user.getMbti().getMbti());
      userDTO.setProfileUrl(user.getProfileImageUrl());

      return userDTO;
   }
   public String getProfileImageUrl(String userId) {
      // 사용자 정보를 DB에서 가져옴
      Optional<User> user = userRepository.findByUserId(userId);

      // MBTI에 맞는 이미지 경로 설정
      String mbti = user.get().getMbti().getMbti();
      String imageFileName = mbti.toLowerCase() + ".png";  // 예: enfp.png, intp.png 등
      String imageUrl = Paths.get(imageDir, imageFileName).toString();

      // 프로필 이미지 URL을 반환
      return imageUrl;
   }
   public Mbti getMbtiByUserId(String userId) {
      User user = userRepository.findByUserId(userId)
              .orElseThrow(() -> new RuntimeException());


      return user.getMbti();
   }
}
