package com.example.cinepick_be.service;

import com.example.cinepick_be.repository.GenreRepository;
import com.example.cinepick_be.repository.MoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {
   private final GenreRepository genreRepository;
   private final MoodRepository moodRepository;


}
