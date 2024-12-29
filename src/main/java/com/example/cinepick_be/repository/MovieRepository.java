package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
   Optional<Movie> findById(Long id);

   Boolean existsByTitle(String title);
}
