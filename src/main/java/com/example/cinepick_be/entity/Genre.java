package com.example.cinepick_be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Genre {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true)
   private String name;

   @ManyToMany
   @JoinTable(
           name="genre_mood",
           joinColumns = @JoinColumn(name="genre_id"),
           inverseJoinColumns = @JoinColumn(name="mood_id")
   )
   private Set<Mood> moods = new HashSet<>();

   public Genre(String name) {
      this.name = name;
   }
}
