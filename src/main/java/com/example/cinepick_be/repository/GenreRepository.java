package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
   Optional<Genre> findById(Long id);

   Genre findByName(String name);
   @Query("SELECT g.name FROM Genre g JOIN g.moods m WHERE m.keyword IN :moodNames")
   List<String> findGenresByMoods(@Param("moodNames") List<String> moodNames);


}
