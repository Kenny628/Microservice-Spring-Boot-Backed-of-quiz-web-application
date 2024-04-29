package com.example.quizservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizservice.model.QuestionWrapper;
import com.example.quizservice.model.QuizDto;
import com.example.quizservice.model.Response;
import com.example.quizservice.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto);

    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitAnswers(@PathVariable int id, @RequestBody List<Response> responses) {
        // TODO: process POST request

        return quizService.submitAnswers(id, responses);
    }

    @GetMapping("getQuestionIds/{id}")
    public ResponseEntity<List<Integer>> getQuestionIds(@PathVariable int id) {
        return quizService.getQuestionIds(id);
    }

}
