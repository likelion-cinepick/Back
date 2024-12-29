package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.Mood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoodRepository extends JpaRepository<Mood, Long> {
   Optional<Mood> findById(Long id);
}
