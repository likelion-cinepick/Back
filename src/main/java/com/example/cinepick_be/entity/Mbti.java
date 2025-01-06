package com.example.cinepick_be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Mbti {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   private String mbti;

   private String profileImage;

   private String person;

   private String quote;

   private String description;

   private String goodChemistry;

   private String badChemistry;

   private String story;

   @OneToMany
   @JoinColumn(name = "mbti_id")
   private List<Movie> recommend = new ArrayList<>();
}
