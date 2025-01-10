package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.Movie;
import com.example.cinepick_be.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
   Optional<Recommend> findById(Long id);

   List<Recommend> findByMovieId(Long id);
}
