package com.example.cinepick_be.controller;

import com.example.cinepick_be.entity.Mbti;
import com.example.cinepick_be.repository.MbtiRepository;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@AllArgsConstructor

public class ImageFileController {



//   @PostMapping("/uploadImage")
//   public String uploadImage(@RequestParam("file") MultipartFile file) {
//      if (file.isEmpty()) {
//         return "No file selected";
//      }
//
//      try {
//         // 파일 저장 경로 생성
//         Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
//         Files.createDirectories(path.getParent()); // 부모 디렉토리 생성
//         file.transferTo(path.toFile());  // 파일 저장
//
//         // 파일 정보를 DB에 저장하는 로직 추가 (예: 파일 경로, 이름 등)
////          imageService.saveFileInfo(file.getOriginalFilename(), path.toString());
//
//         return "File uploaded successfully!";
//      } catch (IOException e) {
//         return "File upload failed: " + e.getMessage();
//      }
//   }
   @Autowired
   private MbtiRepository mbtiRepository;
   @GetMapping("/image/{mbti}")
   public ResponseEntity<FileSystemResource> getImage(@PathVariable String mbti) {
      try {
         Mbti mbtiData = mbtiRepository.findByMbti(mbti);

         String imageFilePath = mbtiData.getProfileImage();
         File imageFile = new File(imageFilePath);

         if (imageFile.exists()) {
            FileSystemResource resource = new FileSystemResource(imageFile);
            return ResponseEntity.ok()
                  .contentType(MediaType.IMAGE_PNG)
                  .body(resource);
         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }

}
