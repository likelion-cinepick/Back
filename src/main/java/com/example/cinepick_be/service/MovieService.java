package com.example.cinepick_be.service;

import com.example.cinepick_be.dto.*;
import com.example.cinepick_be.entity.*;
import com.example.cinepick_be.repository.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieService {

   private final UserRepository userRepository;
   private final MovieRepository movieRepository;
   private final CommentRepository commentRepository;
   private final RecommendRepository recommendRepository;
   private final GenreRepository genreRepository;

   private final RestTemplate restTemplate;

   private static final String API_KEY = "99bf1afb0342f8792e3b76e1d5448639";


   public void fetchAndSaveMovies() {
      int page = 1;
      boolean hasMorePages;

      do {
         String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=ko&page=" + page;

         try {
            ResponseEntity<MovieResponse> response = restTemplate.getForEntity(url, MovieResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
               System.out.println("API call successful");
               System.out.println("Response Body: " + response.getBody());
            } else {
               System.err.println("API call failed with status: " + response.getStatusCode());
            }

            System.out.println(url);

            if (response.getBody() != null && response.getBody().getResults() != null) {
               for (MovieDTO movieDTO : response.getBody().getResults()) {
                  System.out.println(movieDTO);
                  // 영화 제목, 이미지 URL, 장르, 줄거리 처리
                  String title = movieDTO.getTitle();
                  String imageUrl = "https://image.tmdb.org/t/p/w500" + movieDTO.getPosterPath();  // 상대 URL을 절대 URL로 변환
                  String overview = movieDTO.getOverview();

                  // 장르 처리
                  List<Integer> genreIds = movieDTO.getGenreIds(); // 장르 ID 리스트
                  StringBuilder genreString = new StringBuilder();
                  Set<Genre> genreSet = new HashSet();
                  for (Integer genreId : genreIds) {
                     String genreName = genreMap.get(genreId);  // 장르 이름 가져오기
                     if (genreName != null) {
                        // Genre 객체를 찾거나 새로 생성
                        Genre genre = genreRepository.findByName(genreName);
                        if (genre == null) {
                           genre = new Genre();
                           genre.setName(genreName);
                           genreRepository.save(genre);  // 새로운 장르를 저장
                        }
                        genreSet.add(genre);
                     }
                  }
                  System.out.println(title+" "+imageUrl+" "+genreSet+" "+overview);
                  // Save movie information
                  saveMovie(title, imageUrl, genreSet, overview);
               }
               page++;
               hasMorePages = page <= response.getBody().getTotalPages();
            } else {
               hasMorePages = false;
            }
         } catch (Exception e) {
            System.err.println("Error fetching movies on page " + page + ": " + e.getMessage());
            hasMorePages = false;
         }
      } while (hasMorePages);
   }

   private void saveMovie(String title, String imageUrl, Set<Genre> genres, String overview) {

      Movie movie = new Movie();

      // DTO 데이터를 엔티티로 변환
      movie.setTitle(title);
      movie.setPlot(truncatePlot(overview)); // API의 overview 필드를 줄거리로 저장
      movie.setImageUrl(imageUrl); // 포스터 이미지 URL 설정

      // 장르 변환 및 설정
      Set<Genre> genre = genres;
      movie.setGenres(genre);

      // 저장
      movieRepository.save(movie);
   }


   private String truncatePlot(String plot) {
      int maxLength = 10000;  // 최대 길이 설정
      if (plot != null && plot.length() > maxLength) {
         return plot.substring(0, maxLength);  // 최대 길이까지 자르기
      }
      return plot;
   }

   private static final Map<Integer, String> genreMap = Map.ofEntries(
         Map.entry(28, "액션"),
         Map.entry(35, "코미디"),
         Map.entry(18, "드라마"),
         Map.entry(878, "SF"),
         Map.entry(27, "공포"),
         Map.entry(99, "다큐멘터리"),
         Map.entry(10749, "로맨스"),
         Map.entry(10402, "뮤지컬"),
         Map.entry(9648, "미스터리"),
         Map.entry(53, "스릴러"),
         Map.entry(16, "애니메이션"),
         Map.entry(14, "판타지")
   );

   public List<MovieDTO> getAllMovies() {
      List<Movie> movies = movieRepository.findAll(); // DB에서 모든 영화 가져오기

      // Entity -> DTO 변환
      return movies.stream()
            .map(movie -> new MovieDTO(
                  movie.getTitle(),
                  movie.getImageUrl(),
                  movie.getPlot(),
                  genresToString(movie.getGenres())
            ))
            .collect(Collectors.toList());
   }
   public MovieDetailResponseDTO getMovieDetails(Long movieId) {
      // 영화 기본 정보 가져오기
      Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new RuntimeException("Movie not found with id " + movieId));

      // MovieDTO로 변환
      MovieDTO movieDTO = new MovieDTO(
            movie.getTitle(),
            movie.getImageUrl(),
            movie.getPlot(),
            genresToString(movie.getGenres())  // 장르를 문자열로 변환
      );

      // 댓글 리스트 가져오기
      List<CommentDTO> comments = commentRepository.findByMovieId(movieId)
            .stream()
            .map(comment -> new CommentDTO(
                  comment.getId(),
                  comment.getContent(),
                  comment.getUser().getUserId(),
                  comment.getMovie().getId(),
                  comment.getCreatedAt(),
                  comment.getUpdatedAt()
            ))
            .collect(Collectors.toList());

      // 추천 리스트 가져오기
      List<RecommendDTO> recommends = recommendRepository.findByMovieId(movieId)
            .stream()
            .map(recommend -> new RecommendDTO(
                  recommend.getId(),
                  recommend.getContent(),
                  recommend.getUser().getUserId(),
                  recommend.getMovie().getId(),
                  recommend.getCreatedAt()
            ))
            .collect(Collectors.toList());

      // MovieDetailResponseDTO로 반환
      MovieDetailResponseDTO response = new MovieDetailResponseDTO();
      response.setMovieDTO(movieDTO);
      response.setComments(comments);
      response.setRecommends(recommends);

      return response;
   }


   private String genresToString(Set<Genre> genres) {
      if (genres == null || genres.isEmpty()) {
         return "Unknown";  // 장르가 없을 경우 기본값
      }
      return genres.stream()
            .map(Genre::getName)  // Genre 객체에서 이름을 가져옴
            .collect(Collectors.joining(", "));  // 콤마로 구분된 문자열로 변환
   }


   public void addComment(Long movieId,CommentDTO commentDTO){
      User user = userRepository.findByUserId(commentDTO.getUserId())
              .orElseThrow(()->new IllegalArgumentException());
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()-> new IllegalArgumentException());

      Comment comment = new Comment();
      comment.setUser(user);
      comment.setMovie(movie);
      comment.setContent(commentDTO.getContent());

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
      recommend.setContent(recommendDTO.getContent());

      recommendRepository.save(recommend);

   }

   public void deleteRecommend(Long id){
      Recommend recommend= recommendRepository.findById(id)
              .orElseThrow(()->new IllegalArgumentException());

      recommendRepository.delete(recommend);
   }

   public List<RecommendDTO> recommends(Long id){
//      Movie movie= movieRepository.findById(id)
//              .orElseThrow(()-> new IllegalArgumentException("해당 영화가 존재하지 않습니다."));
      List<Recommend> recommends=recommendRepository.findByMovieId(id);
      return recommends.stream()
              .map(recommend -> new RecommendDTO(recommend)).collect(Collectors.toList());
   }
}
