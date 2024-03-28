package com.example.questionservice.model;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("QUIZ-SERVICE")
public interface QuestionInterface {
    
@GetMapping("quiz/getQuestionIds/{id}")
public ResponseEntity<List<Integer>> getQuestionIds(@PathVariable int id);
}
