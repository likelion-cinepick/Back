package com.example.cinepick_be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Question {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   private String category;

   private String question;
}


