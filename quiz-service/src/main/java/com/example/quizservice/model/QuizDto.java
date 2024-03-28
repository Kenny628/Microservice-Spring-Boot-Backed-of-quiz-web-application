package com.example.quizservice.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QuizDto {

    String categoryName;
    int numQuestions;
    String title;
}
