package com.example.cinepick_be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Mood {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   private String keyword;

   @ManyToMany(mappedBy = "moods")
   private Set<Genre> genres= new HashSet<>();
}
