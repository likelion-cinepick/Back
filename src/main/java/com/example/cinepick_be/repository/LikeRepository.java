package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.Like;
import com.example.cinepick_be.entity.Movie;
import com.example.cinepick_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
   List<Like> findByUser(User user);
   Optional<Like> findByUserAndMovie(User user, Movie movie);
}
