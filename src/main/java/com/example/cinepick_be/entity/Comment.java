package com.example.cinepick_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Comment {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   @Column(nullable= false, columnDefinition = "TEXT")
   private String content;

   @CreatedDate
   private LocalDateTime createdAt;

   @LastModifiedDate
   private LocalDateTime updatedAt;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="user_id", nullable = false)
   private User user;

   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="movie_id", nullable=false)
   private Movie movie;
}
