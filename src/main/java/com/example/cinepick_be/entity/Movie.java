package com.example.cinepick_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie")
public class Movie {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   private String title;

   private String imageUrl;
   @Lob
   private String plot;

   @ManyToMany(fetch = FetchType.LAZY)
   @JoinTable(
           name= "movie_genre",
           joinColumns = @JoinColumn(name= "movie_id"),
           inverseJoinColumns = @JoinColumn(name="genre_id")
   )
   @JsonIgnoreProperties("movies")
   private List<Genre> genres= new ArrayList<>();

   @OneToMany(mappedBy = "movie", cascade= CascadeType.ALL)
   private List<Like> likeList=new ArrayList<>();

   @OneToMany(mappedBy="movie", cascade = CascadeType.ALL)
   private List<Recommend> recommendList= new ArrayList<>();

   @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
   private List<Comment> comment;

   @ManyToOne
   @JoinColumn(name = "mbti_id")
   @JsonManagedReference
   private Mbti mbti;

}
