package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MbtiRepository extends JpaRepository<Mbti, Long> {
   Mbti findByMbti(String mbti);

}
