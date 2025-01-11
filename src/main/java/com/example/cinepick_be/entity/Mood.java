package com.example.cinepick_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Mood {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   private String keyword;

   @ManyToMany(mappedBy = "moods")
   @JsonIgnoreProperties("moods")
   private List<Genre> genres= new ArrayList<>();
}
