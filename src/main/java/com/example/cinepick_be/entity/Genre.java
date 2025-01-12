package com.example.cinepick_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "genre")
public class Genre {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true)
   private String name;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(
           name="genre_mood",
           joinColumns = @JoinColumn(name="genre_id"),
           inverseJoinColumns = @JoinColumn(name="mood_id")
   )
   @JsonIgnoreProperties("genres")
   private List<Mood> moods = new ArrayList<>();

   @ManyToMany(mappedBy = "genres")
   private List<Movie> movies = new ArrayList<>();
   public Genre(String name) {
      this.name = name;
   }
}
