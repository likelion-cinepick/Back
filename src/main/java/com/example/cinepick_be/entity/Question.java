package com.example.cinepick_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   private String question;

   @ElementCollection
   @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
   @Column(name = "option_value")
   private List<String> options;


   public Question(String question, List<String> options) {
      this.question=question;
      this.options = options;
   }
}


