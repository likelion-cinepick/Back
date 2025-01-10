package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
   Optional<Genre> findById(Long id);

   Genre findByName(String name);

}
