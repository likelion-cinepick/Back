package com.example.cinepick_be.controller;

import com.example.cinepick_be.dto.CommentDTO;
import com.example.cinepick_be.dto.MovieDetailResponseDTO;
import com.example.cinepick_be.dto.RecommendDTO;
import com.example.cinepick_be.entity.Comment;
import com.example.cinepick_be.entity.Movie;
import com.example.cinepick_be.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

   private final MovieService movieService;

   @PostMapping("/fetch")
   public ResponseEntity<String> fetchMovies() {
      movieService.saveMoviesFromApi();
      return ResponseEntity.ok("Movies have been fetched and saved to the database.");
   }

   @GetMapping("/all")
   public ResponseEntity<List<Movie>> getAllMovies() {
      List<Movie> movies = movieService.getAllMovies();
      return ResponseEntity.ok(movies);
   }
   //영화 상세 페이지
   @GetMapping("/{movieId}")
   public ResponseEntity<MovieDetailResponseDTO> getMovieById(@PathVariable Long movieId) {
      MovieDetailResponseDTO movie = movieService.getMovieDetails(movieId);
      return ResponseEntity.ok(movie);
   }

   //댓글 추가
   @PostMapping("/{movieId}/comment")
   public ResponseEntity<String> addComment(
      @PathVariable Long movieId,
      @RequestBody CommentDTO commentDTO
   ){
      movieService.addComment(movieId,commentDTO);
      return ResponseEntity.ok("댓글이 추가되었습니다.");
   }
   //댓글 수정
   @PutMapping("/{movieId}/comment/{commentId}")
   public ResponseEntity<String> editComment(
      @PathVariable Long commentId,
      @RequestBody CommentDTO commentDTO
      ){
      movieService.editComment(commentId,commentDTO);
      return ResponseEntity.ok("댓글이 수정되었습니다.");
   }
   //댓글 삭제
   @DeleteMapping("/{movieId}/comment/{commentId}")
   public ResponseEntity<Void> deleteComment(
           @PathVariable Long commentId){
      movieService.deleteComment(commentId);
      return ResponseEntity.noContent().build();
   }
   //추천 활동 추가
   @PostMapping("/{movieId}/recommend")
   public ResponseEntity<String> addRecommend(
           @PathVariable Long movieId,
           @RequestBody RecommendDTO recommendDTO
           ){
      movieService.addRecommend(movieId, recommendDTO);
      return ResponseEntity.ok("추천 활동이 추가되었습니다.");
   }
   //추천 활동 삭제
   @DeleteMapping("/{movieId}/recommend/{recommendId}")
   public ResponseEntity<String> deleteRecommend(
           @PathVariable Long recommendId
   ){
      movieService.deleteRecommend(recommendId);
      return ResponseEntity.ok("추천 활동을 삭제하였습니다.");
   }

   //추천 활동 조회
   @GetMapping("/{movieId}/recommends")
   public ResponseEntity<List<RecommendDTO>> recommends(
           @PathVariable Long movieId
   ){
      List<RecommendDTO> recommendDTOS= movieService.recommends(movieId);
      return ResponseEntity.ok(recommendDTOS);
   }
}
