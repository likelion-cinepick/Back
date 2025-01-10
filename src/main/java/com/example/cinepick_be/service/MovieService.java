package com.example.cinepick_be.service;

import com.example.cinepick_be.dto.*;
import com.example.cinepick_be.entity.*;
import com.example.cinepick_be.repository.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

   private final UserRepository userRepository;
   private final MovieRepository movieRepository;
   private final CommentRepository commentRepository;
   private final RecommendRepository recommendRepository;
   private final GenreRepository genreRepository;

//   @Value("${kmdb.api.url}")
//   private String kmdbApiUrl;
   //   public MovieDTO getMovieDetails(String movieCd) {
//      RestTemplate restTemplate = new RestTemplate();
//
//      // API 요청 URL 생성
//      String requestUrl = API_URL + "?key=" + API_KEY + "&movieCd=" + movieCd;
//
//      // API 호출
//      String response = restTemplate.getForObject(requestUrl, String.class);
//
//      // JSON 파싱
//      JSONObject jsonResponse = new JSONObject(response);
//      JSONObject movieInfo = jsonResponse.getJSONObject("movieInfoResult").getJSONObject("movieInfo");
//
//      // 데이터 추출
//      String title = movieInfo.getString("movieNm");
//      String plot = movieInfo.optString("plot", "줄거리 없음");
//      String poster = movieInfo.optString("posters", "포스터 없음");
//
//      // 장르 처리
//      JSONArray genresArray = movieInfo.getJSONArray("genres");
//      StringBuilder genres = new StringBuilder();
//      for (int i = 0; i < genresArray.length(); i++) {
//         if (i > 0) genres.append(", ");
//         genres.append(genresArray.getJSONObject(i).getString("genreNm"));
//      }
//
//      // DTO 반환
//      return new MovieDTO(title, poster, plot, genres.toString());
//   }
//   public void fetchAndSaveMovies() {
//      int batchSize = 100; // 한번에 처리할 데이터 개수
//      int page = 1;
//      int totalFetched = 0;
//
//      while (totalFetched < 3000) {
//         String url = API_URL + "?key=" + API_KEY + "&page=" + page;
//         ResponseEntity<ApiResponse> response = restTemplate.exchange(
//               url, HttpMethod.GET, null, new ParameterizedTypeReference<ApiResponse>() {});
//
//         if (response.getBody() == null || response.getBody().getClass() == null) {
//            break;
//         }
//
//         List<MovieDTO> movies = (List<MovieDTO>) response.getBody();
//         saveMovies(movies);
//
//         totalFetched += movies.size();
//         page++;
//         if (movies.size() < batchSize) {
//            break; // 더 이상 가져올 데이터가 없으면 중단
//         }
//      }
//   }
   //   public void saveMoviesFromApi() {
//      String url = kmdbApiUrl + "/movies?count=1000";  // API URL 및 쿼리 파라미터 예시
//
//      // KMDb API에서 영화 리스트 가져오기
//      MovieDTO[] movieDtos = restTemplate.getForObject(url, MovieDTO[].class);
//
//      // 가져온 데이터 DB에 저장
//      if (movieDtos != null) {
//         for (MovieDTO movieDto : movieDtos) {
//            boolean exists = movieRepository.existsByTitle(movieDto.getTitle());
//            if (!exists) {
//               Movie movie = new Movie();
//               movie.setTitle(movieDto.getTitle());
//               movie.setImageUrl(movieDto.getImageUrl());
//               movie.setPlot(movieDto.getPlot());
//               Set<Genre> genres = new HashSet<>();
//               if (movieDto.getGenre() != null) {
//                  String[] genreNames = movieDto.getGenre().split(",");
//                  for (String genreName : genreNames) {
//                     Genre genre = genreRepository.findByName(genreName)
//                           .orElseGet(() -> genreRepository.save(new Genre(genreName)));
//                     genres.add(genre);
//                  }
//               }
//               movie.setGenres(genres);
//
//               movieRepository.save(movie);  // 데이터베이스에 저장
//            }
//         }
//      }
//   }

   private final RestTemplate restTemplate;

   private static final String API_URL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
   private static final String API_KEY = "0674387425b773a2e93c101d3eff771e";

   // DB에 저장
   private void saveMovies(List<MovieDTO> movieDTOs) {
      for (MovieDTO dto : movieDTOs) {
         if (!movieRepository.existsByTitle(dto.getTitle())) {
            Movie movie = new Movie();
            movie.setTitle(dto.getTitle());
            movie.setImageUrl(dto.getImageUrl());
            movie.setPlot(dto.getPlot());

            String[] genreNames = dto.getGenre().split(",");
            Set<Genre> genres = new HashSet<>();
            for (String genreName : genreNames) {
               Genre genre = genreRepository.findByName(genreName.trim())
                     .orElseGet(() -> genreRepository.save(new Genre(genreName.trim())));
               genres.add(genre);
            }
            movie.setGenres(genres);

            movieRepository.save(movie);
         }
      }
   }

   //특정 영화 상세 정보 가져오기
   public MovieDTO getMovieDetails(String movieCd) {
      String requestUrl = API_URL + "?key=" + API_KEY + "&movieCd=" + movieCd;
      String response = restTemplate.getForObject(requestUrl, String.class);

      JSONObject jsonResponse = new JSONObject(response);
      JSONObject movieInfo = jsonResponse.getJSONObject("movieInfoResult").getJSONObject("movieInfo");

      String title = movieInfo.getString("movieNm");
      String plot = movieInfo.optString("plot", "줄거리 없음");
      String poster = movieInfo.optString("posters", "포스터 없음");

      JSONArray genresArray = movieInfo.getJSONArray("genres");
      StringBuilder genres = new StringBuilder();
      for (int i = 0; i < genresArray.length(); i++) {
         if (i > 0) genres.append(", ");
         genres.append(genresArray.getJSONObject(i).getString("genreNm"));
      }

      return new MovieDTO(title, poster, plot, genres.toString());
   }

   // 영화진흥위원회 API에서 영화 목록 가져와서 저장
   public void fetchAndSaveMovies() {
      int page = 1;
      int totalFetched = 0;
      final int maxMovies = 3000;
      final int batchSize = 100;

      while (totalFetched < maxMovies) {
         // 올바른 API URL 사용
         String url = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=" + API_KEY + "&curPage=" + page;

         try {
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("API Response: " + response);  // 응답 확인용 출력

            JSONObject jsonResponse = new JSONObject(response);
            // movieListResult 확인
            if (jsonResponse.has("movieListResult")) {
               JSONObject movieListResult = jsonResponse.getJSONObject("movieListResult");
               JSONArray moviesArray = movieListResult.getJSONArray("movieList");

               List<MovieDTO> movieDTOs = new ArrayList<>();
               for (int i = 0; i < moviesArray.length(); i++) {
                  JSONObject movie = moviesArray.getJSONObject(i);
                  String movieCd = movie.getString("movieCd");
                  MovieDTO movieDTO = getMovieDetails(movieCd);
                  movieDTOs.add(movieDTO);
               }

               saveMovies(movieDTOs);

               totalFetched += movieDTOs.size();
               page++;
               if (movieDTOs.size() < batchSize) break; // 더 이상 데이터가 없으면 중단
            } else {
               System.out.println("Error: movieListResult not found in response.");
               break;
            }
         } catch (Exception e) {
            System.err.println("Error fetching movies on page " + page + ": " + e.getMessage());
            break;
         }
      }
   }

   public List<MovieDTO> getAllMovies() {
      List<Movie> movies = movieRepository.findAll();

      return movies.stream().map(this::convertToDto)
            .collect(Collectors.toList());
   }
   private MovieDTO convertToDto(Movie movie) {
      MovieDTO movieDTO = new MovieDTO();
      movieDTO.setTitle(movie.getTitle());
      movieDTO.setImageUrl(movie.getImageUrl());
      movieDTO.setPlot(movie.getPlot());

      return movieDTO;
   }
   //영화 상세 페이지
   public MovieDetailResponseDTO getMovieDetails(Long movieId){
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(()-> new IllegalArgumentException("영화가 존재하지 않습니다."));

      MovieDTO movieDTO= new MovieDTO(movie.getTitle(), movie.getImageUrl(), movie.getPlot(), movie.getGenres().toString());

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
      Movie movie= movieRepository.findById(id)
              .orElseThrow(()-> new IllegalArgumentException("해당 영화가 존재하지 않습니다."));
      List<Recommend> recommends=recommendRepository.findByMovie(movie);
      return recommends.stream()
              .map(recommend -> new RecommendDTO(recommend)).collect(Collectors.toList());
   }
}
