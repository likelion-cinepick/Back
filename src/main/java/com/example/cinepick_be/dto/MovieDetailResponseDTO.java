package com.example.cinepick_be.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieDetailResponseDTO {
   private MovieDTO movieDTO;
   private List<CommentDTO> comments;
   private List<RecommendDTO> recommends;
}
