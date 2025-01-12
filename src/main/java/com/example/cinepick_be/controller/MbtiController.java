package com.example.cinepick_be.controller;

import com.example.cinepick_be.dto.MbtiDTO;
import com.example.cinepick_be.dto.MbtiResultDTO;
import com.example.cinepick_be.dto.UserDTO;
import com.example.cinepick_be.entity.Mbti;
import com.example.cinepick_be.entity.Question;
import com.example.cinepick_be.entity.User;
import com.example.cinepick_be.repository.MbtiRepository;
import com.example.cinepick_be.repository.UserRepository;
import com.example.cinepick_be.service.QuestionService;
import com.example.cinepick_be.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class MbtiController {

   @Value("${file.upload-dir}")
   private String uploadDir;

   private final UserService userService;
   private final UserRepository userRepository;
   private final MbtiRepository mbtiRepository;
   private final QuestionService questionService;

   @PostMapping("/submit")
   public ResponseEntity<UserDTO> mbtiResult(@RequestBody MbtiResultDTO mbtiResultDTO){
      if(mbtiResultDTO.getMbti() == null || mbtiResultDTO.getMbti().size() !=4)
         return ResponseEntity.badRequest().build();

      String result = String.join("", mbtiResultDTO.getMbti());

      UserDTO user= userService.updateUserWithMbti(mbtiResultDTO.getUserId(),result);

      return ResponseEntity.ok(user);


   }

   @GetMapping
   public List<Question> getQuestion(){
      return questionService.getAllQuestion();
   }

   @GetMapping("/result")
   public ResponseEntity<MbtiDTO> getUser(Authentication authentication) {
      String userId =authentication.getName();
      User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

      UserDTO userDTO = new UserDTO(user);

      Mbti mbti = mbtiRepository.findByMbti(userDTO.getMbti());


      MbtiDTO mbtiDTO = new MbtiDTO();
      mbtiDTO.setMbti(mbti.getMbti());
      mbtiDTO.setPerson(mbti.getPerson());
      mbtiDTO.setQuote(mbti.getQuote());
      mbtiDTO.setDescription(mbti.getDescription());
      mbtiDTO.setStory(mbti.getStory());

      mbtiDTO.setProfileUrl("http://3.105.163.214:8080/uploads/mbti/" + mbti.getProfileImage());
//      mbtiDTO.setProfileUrl("http://localhost:8080/uploads/mbti/" + mbti.getProfileImage());

      mbtiDTO.setGoodChemistry(mbti.getGoodChemistry().getMbti());
      mbtiDTO.setBadChemistry(mbti.getBadChemistry().getMbti());
      mbtiDTO.setRecommend(mbti.getRecommend());

      String imageFilePath = mbti.getProfileImage();

      return ResponseEntity.ok(mbtiDTO);
   }
}
