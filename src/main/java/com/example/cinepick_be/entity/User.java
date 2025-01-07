package com.example.cinepick_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "\"user\"")
public class User {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;

   @Column(unique= true)
   private String userId;

   @Column(nullable=false)
   private String password;

   private String nickname;

   @ManyToOne
   @JoinColumn(name="mbti_id")
   @JsonIgnore
   private Mbti mbti;

   private String profileImageUrl;

   @ElementCollection(fetch= FetchType.EAGER)
   @CollectionTable(name= "user_mood", joinColumns = @JoinColumn(name= "userId"))
   private List<String> moodList= new ArrayList<>();

   @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
   private List<Like> likeList=new ArrayList<>();

   @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
   private List<Recommend> recommendList= new ArrayList<>();

   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   private List<Comment> comment;

}
