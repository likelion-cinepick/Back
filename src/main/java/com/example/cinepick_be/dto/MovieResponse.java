package com.example.cinepick_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
   private int page;
   private List<MovieDTO> results;
   private int totalPages;
   private int totalResults;
}
