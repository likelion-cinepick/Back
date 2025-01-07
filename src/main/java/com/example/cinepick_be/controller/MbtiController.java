package com.example.cinepick_be.controller;

import com.example.cinepick_be.dto.MbtiDTO;
import com.example.cinepick_be.dto.MbtiResultDTO;
import com.example.cinepick_be.dto.UserDTO;
import com.example.cinepick_be.entity.Mbti;
import com.example.cinepick_be.entity.User;
import com.example.cinepick_be.repository.MbtiRepository;
import com.example.cinepick_be.repository.UserRepository;
import com.example.cinepick_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cinepick/test")
@RequiredArgsConstructor
public class MbtiController {

   private final UserService userService;
   private final UserRepository userRepository;
   private final MbtiRepository mbtiRepository;

   @PostMapping("/result")
   public ResponseEntity<UserDTO> mbtiResult(@RequestBody MbtiResultDTO mbtiResultDTO){
      if(mbtiResultDTO.getMbti() == null || mbtiResultDTO.getMbti().size() !=4)
         return ResponseEntity.badRequest().build();

      String result = String.join("", mbtiResultDTO.getMbti());

      UserDTO user= userService.updateUserWithMbti(mbtiResultDTO.getUserId(),result);

      return ResponseEntity.ok(user);


   }

   @GetMapping("/result/{userId}")
   public ResponseEntity<MbtiDTO> getUser(@PathVariable String userId) {
      User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

      // UserDTO 생성
      UserDTO userDTO = new UserDTO(user);

      // UserDTO의 mbtiType을 가지고 해당하는 Mbti 엔티티 찾기
      Mbti mbti = mbtiRepository.findByMbti(userDTO.getMbti());


      // MbtiDTO 생성
      MbtiDTO mbtiDTO = new MbtiDTO();
      mbtiDTO.setMbti(mbti.getMbti());
      mbtiDTO.setPerson(mbti.getPerson());
      mbtiDTO.setQuote(mbti.getQuote());
      mbtiDTO.setDescription(mbti.getDescription());
      mbtiDTO.setStory(mbti.getStory());
      mbtiDTO.setProfileUrl(mbti.getProfileImage());
      mbtiDTO.setGoodChemistry(mbti.getGoodChemistry().getMbti());
      mbtiDTO.setBadChemistry(mbti.getBadChemistry().getMbti());
      mbtiDTO.setRecommend(mbti.getRecommend());

      return ResponseEntity.ok(mbtiDTO);  // MbtiDTO 반환
   }

}
