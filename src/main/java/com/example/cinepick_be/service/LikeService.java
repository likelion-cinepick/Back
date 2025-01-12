package com.example.cinepick_be.service;

import com.example.cinepick_be.dto.LikeDTO;
import com.example.cinepick_be.entity.Like;
import com.example.cinepick_be.entity.Movie;
import com.example.cinepick_be.entity.User;
import com.example.cinepick_be.repository.LikeRepository;
import com.example.cinepick_be.repository.MovieRepository;
import com.example.cinepick_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

   private final UserRepository userRepository;
   private final LikeRepository likeRepository;
   private final MovieRepository movieRepository;
   public List<LikeDTO> like(String userId){
      User user =userRepository.findByUserId(userId)
              .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다."));

      List<Like> likes= likeRepository.findByUser(user);

      return likes.stream().map(like-> new LikeDTO(
              like.getUser().getUserId(),
              like.getMovie()
      )).collect(Collectors.toList());
   }

   public ResponseEntity<String> addOrRemoveLike(String userId, Long movieId){
      User user =userRepository.findByUserId(userId)
              .orElseThrow(()-> new IllegalArgumentException());
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(() -> new IllegalArgumentException());

      Optional<Like> existLike= likeRepository.findByUserAndMovie(user,movie);
      if(existLike.isPresent()){
         likeRepository.delete(existLike.get());
         return ResponseEntity.ok("좋아요가 취소되었습니다.");
      }else{
         Like like =new Like();
         like.setUser(user);
         like.setMovie(movie);
         likeRepository.save(like);
         return ResponseEntity.ok("좋아요가  추가되었습니다");
      }
   }

//   public boolean addLike(Long movieId, String userId) {
//      Movie movie = movieRepository.findById(movieId)
//            .orElseThrow(() -> new IllegalArgumentException());
//      User user = userRepository.findByUserId(userId)
//            .orElseThrow(()-> new IllegalArgumentException());
//      Like like = new Like();
//      like.setMovie(movie);
//      like.setUser(user);
//      if(!likeRepository.existByUserAndMovie(like)) {
//         likeRepository.save(like);
//         return true;
//      }else return false;
//
//   }
}
