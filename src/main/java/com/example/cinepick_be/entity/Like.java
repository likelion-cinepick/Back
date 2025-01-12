package com.example.cinepick_be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name="movie_like")
@Data
public class
Like {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "movie_id", nullable = false)
   private Movie movie;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", nullable = false)
   private User user;
}
