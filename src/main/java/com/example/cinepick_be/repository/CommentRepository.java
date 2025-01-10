package com.example.cinepick_be.repository;

import com.example.cinepick_be.entity.Comment;
import com.example.cinepick_be.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
   //해당 id 댓글 조회
   Optional<Comment> findById(Long id);


   List<Comment> findByMovieId(Long movieId);
}
