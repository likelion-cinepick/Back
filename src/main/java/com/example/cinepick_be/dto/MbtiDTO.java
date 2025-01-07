package com.example.cinepick_be.dto;

import com.example.cinepick_be.entity.Mbti;
import com.example.cinepick_be.entity.Movie;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MbtiDTO {
   private String mbti;

   private String profileUrl;

   private String person;

   private String quote;

   @Lob
   private String description;


   private String goodChemistry;


   private String badChemistry;

   @Lob
   private String story;

   private List<Movie> recommend = new ArrayList<>();


}
