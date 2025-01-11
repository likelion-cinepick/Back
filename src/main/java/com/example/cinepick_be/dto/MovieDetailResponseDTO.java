package com.example.cinepick_be.dto;

import com.example.cinepick_be.entity.Comment;
import com.example.cinepick_be.entity.Recommend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailResponseDTO {
   private MovieDTO movieDTO;
   private List<CommentDTO> comments;
   private List<RecommendDTO> recommends;

   public MovieDetailResponseDTO(String title, String imageUrl, String plot, String genre, List<CommentDTO> comment, List<RecommendDTO> recommendList) {
      // MovieDTO 객체를 초기화하여 설정
      this.movieDTO = new MovieDTO();
      this.movieDTO.setTitle(title); // title 설정
      this.movieDTO.setPosterPath(imageUrl); // imageUrl 설정
      this.movieDTO.setOverview(plot);
      this.movieDTO.setGenre(genre);
      this.comments = comment;
      this.recommends = recommendList;
   }

}
