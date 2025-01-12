package com.example.cinepick_be.service;

import com.example.cinepick_be.entity.Question;
import com.example.cinepick_be.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
   private final QuestionRepository questionRepository;

   public List<Question> getAllQuestion(){
      return questionRepository.findAll();
   }
}
