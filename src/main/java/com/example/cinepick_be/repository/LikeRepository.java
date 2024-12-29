package com.example.cinepick_be.repository;

import com.example.cinepick_be.dto.LikeDTO;
import com.example.cinepick_be.entity.Like;
import com.example.cinepick_be.entity.Movie;
import com.example.cinepick_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
   List<Like> findByUser(User user);

   Optional<Like> findByLike(User user, Movie movie);
}
