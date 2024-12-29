package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   Optional<User> findByUserId(String id);
   Optional<User> findById(Long id);

   Boolean existsByUserId(String id);
}
