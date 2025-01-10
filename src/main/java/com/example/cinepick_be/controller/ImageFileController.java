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
import java.nio.file.Path;
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
   @CrossOrigin(origins = "http://your-frontend-url")
   @GetMapping("/image/{mbti}")
   public ResponseEntity<FileSystemResource> getImage(@PathVariable String mbti) {
      try {
         Mbti mbtiData = mbtiRepository.findByMbti(mbti);

         String imageFilePath = "/home/ubuntu/cinepick-be/" +mbtiData.getProfileImage();
         Path path = Paths.get(imageFilePath).toAbsolutePath();  // 절대 경로로 변환


         System.out.println(path);
         File imageFile = path.toFile();  // File 객체로 변환

         if (imageFile.exists()) {
            // 이미지의 확장자에 맞게 MediaType 설정
            String fileExtension = imageFile.getName().substring(imageFile.getName().lastIndexOf(".") + 1);
            MediaType mediaType = MediaType.IMAGE_PNG; // 기본값

            // JPG 형식 처리
            if (fileExtension.equalsIgnoreCase("jpeg") || fileExtension.equalsIgnoreCase("jpg")) {
               mediaType = MediaType.IMAGE_JPEG;
            }

            FileSystemResource resource = new FileSystemResource(imageFile);
            return ResponseEntity.ok()
                  .contentType(mediaType)
                  .body(resource);
         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
      } catch (Exception e) {
         // 에러가 발생한 경우
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }
}
