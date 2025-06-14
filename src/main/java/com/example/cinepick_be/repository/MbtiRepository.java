package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.Genre;
import com.example.cinepick_be.entity.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MbtiRepository extends JpaRepository<Mbti, Long> {
   Mbti findByMbti(String mbti);

   @Query("SELECT genres FROM Mbti WHERE mbti = :mbti")
   List<Genre> findGenresByMbtiId(String mbti);

   Optional<Mbti> findById(Long id);
}
