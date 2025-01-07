package com.example.cinepick_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"goodChemistry", "badChemistry"})
public class Mbti {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   private String mbti;

   private String profileImage;

   private String person;

   private String quote;

   @Lob
   private String description;

   @ManyToOne
   @JoinColumn(name = "good_chemistry_id")
   @JsonManagedReference
   private Mbti goodChemistry;

   @ManyToOne
   @JoinColumn(name = "bad_chemistry_id")
   @JsonManagedReference
   private Mbti badChemistry;

   @Lob
   private String story;

   @OneToMany
   @JoinColumn(name = "mbti_id")
   private List<Movie> recommend = new ArrayList<>();


}
