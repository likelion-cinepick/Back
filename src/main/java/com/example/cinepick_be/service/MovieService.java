package com.example.cinepick_be.service;

import com.example.cinepick_be.dto.*;
import com.example.cinepick_be.entity.Comment;
import com.example.cinepick_be.entity.Movie;
import com.example.cinepick_be.entity.Recommend;
import com.example.cinepick_be.entity.User;
import com.example.cinepick_be.repository.CommentRepository;
import com.example.cinepick_be.repository.MovieRepository;
import com.example.cinepick_be.repository.RecommendRepository;
import com.example.cinepick_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

   private final UserRepository userRepository;
   private final MovieRepository movieRepository;
   private final CommentRepository commentRepository;
   private final RecommendRepository recommendRepository;

   @Value("${kmdb.api.url}") // KMDb API URL을 application.properties에 설정
   private String kmdbApiUrl;

   private final RestTemplate restTemplate;  // RestTemplate 객체를 주입받기

   // KMDb API에서 1000개 영화 정보 가져오기
   public void saveMoviesFromApi() {
      String url = kmdbApiUrl + "/movies?count=1000";  // API URL 및 쿼리 파라미터 예시

      // KMDb API에서 영화 리스트 가져오기
      MovieDTO[] movieDtos = restTemplate.getForObject(url, MovieDTO[].class);

      // 가져온 데이터 DB에 저장
      if (movieDtos != null) {
         for (MovieDTO movieDto : movieDtos) {
            boolean exists = movieRepository.existsByTitle(movieDto.getTitle());
            if (!exists) {
               Movie movie = new Movie();
               movie.setTitle(movieDto.getTitle());
               movie.setImageUrl(movieDto.getImageUrl());
               movie.setPlot(movieDto.getPlot());
               movie.setGenre(movieDto.getGenre());

               movieRepository.save(movie);  // 데이터베이스에 저장
            }
         }
      }
   }

   public List<Movie> getAllMovies() {
      return movieRepository.findAll();  // DB에서 모든 영화 목록 가져오기
   }

   //영화 상세 페이지
   public MovieDetailResponseDTO getMovieDetails(Long movieId){
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()-> new IllegalArgumentException("영화가 존재하지 않습니다."));

      MovieDTO movieDTO= new MovieDTO(movie.getTitle(), movie.getImageUrl(), movie.getPlot(), movie.getGenre());

      List<Comment> comments = commentRepository.findByMovie(movie);
      List<CommentDTO> commentDTOs= comments.stream()
              .map(c-> {
                 CommentDTO commentDTO= new CommentDTO();
                 commentDTO.setContent(c.getContent());
                 commentDTO.setCreatedAt(c.getCreatedAt());
                 return commentDTO;
                      }).collect(Collectors.toList());

      List<Recommend> recommends= recommendRepository.findByMovie(movie);
      List<RecommendDTO> recommendDTOs= recommends.stream()
              .map(c->{
                  RecommendDTO recommendDTO= new RecommendDTO();
                  recommendDTO.setContent(c.getContent());
                  recommendDTO.setCreatedAt(c.getCreatedAt());
                  return recommendDTO;
              }).collect(Collectors.toList());

      MovieDetailResponseDTO response= new MovieDetailResponseDTO();
      response.setMovieDTO(movieDTO);
      response.setComments(commentDTOs);
      response.setRecommends(recommendDTOs);

      return response;

   }
   public void addComment(Long movieId,CommentDTO commentDTO){
      User user = userRepository.findByUserId(commentDTO.getUserId())
              .orElseThrow(()->new IllegalArgumentException());
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()-> new IllegalArgumentException());

      Comment comment = new Comment();
      comment.setUser(user);
      comment.setMovie(movie);

      commentRepository.save(comment);
   }

   public void editComment(Long commentId, CommentDTO commentDTO){
      Comment comment = commentRepository.findById(commentId)
              .orElseThrow(()-> new IllegalArgumentException());

      if(commentDTO.getContent() !=null){
         comment.setContent(commentDTO.getContent());
      }
      comment.setUpdatedAt(LocalDateTime.now());
      commentRepository.save(comment);
   }

   public void deleteComment(Long id){
      Comment comment = commentRepository.findById(id)
              .orElseThrow(()-> new IllegalArgumentException());
      commentRepository.delete(comment);
   }

   public void addRecommend(Long movieId, RecommendDTO recommendDTO){
      User user = userRepository.findByUserId(recommendDTO.getUserId())
              .orElseThrow(()->new IllegalArgumentException());
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()-> new IllegalArgumentException());

      Recommend recommend= new Recommend();
      recommend.setUser(user);
      recommend.setMovie(movie);

      recommendRepository.save(recommend);

   }

   public void deleteRecommend(Long id){
      Recommend recommend= recommendRepository.findById(id)
              .orElseThrow(()->new IllegalArgumentException());

      recommendRepository.delete(recommend);
   }

   public List<RecommendDTO> recommends(Long id){
      Movie movie= movieRepository.findById(id)
              .orElseThrow(()-> new IllegalArgumentException("해당 영화가 존재하지 않습니다."));
      List<Recommend> recommends=recommendRepository.findByMovie(movie);
      return recommends.stream()
              .map(recommend -> new RecommendDTO(recommend)).collect(Collectors.toList());
   }
}
