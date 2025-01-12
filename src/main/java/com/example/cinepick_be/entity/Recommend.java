package com.example.cinepick_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "recommend")
public class Recommend {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, columnDefinition = "TEXT")
   private String content;

   @CreatedDate
   private LocalDateTime createdAt;

   @ManyToOne(fetch= FetchType.LAZY)
   @JoinColumn(name= "user_id", nullable = false)
   private User user;

   @ManyToOne(fetch= FetchType.LAZY)
   @JoinColumn(name= "movie_id", nullable =false)
   private Movie movie;
}
