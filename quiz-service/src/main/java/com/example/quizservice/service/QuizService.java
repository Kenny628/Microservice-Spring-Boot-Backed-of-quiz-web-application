package com.example.quizservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.quizservice.dao.QuizDao;

import com.example.quizservice.model.QuestionWrapper;
import com.example.quizservice.model.Quiz;
import com.example.quizservice.model.QuizDto;
import com.example.quizservice.model.QuizInterface;
import com.example.quizservice.model.Response;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        List<Integer> questions=quizInterface.getQuestionsForQuiz(quizDto.getCategoryName(),quizDto.getNumQuestions()).getBody();
        Quiz quiz=new Quiz();
        quiz.setTitle(quizDto.getTitle());
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }
    public ResponseEntity<List<QuestionWrapper>> getQuiz(int id) {
        Quiz quiz=quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions=quizInterface.getQuestions(questionIds);
        return questions;
    }
    public ResponseEntity<Integer> submitAnswers(int id, List<Response> responses) {
        ResponseEntity<Integer> result=quizInterface.getScores(responses);
        return result;
    }
	public ResponseEntity<List<Integer>> getQuestionIds(int id) {
        Optional <Quiz> questionIdListOptional=quizDao.findById(id);
        List<Integer> questionIdList=questionIdListOptional.get().getQuestionIds();
        return new ResponseEntity<>(questionIdList,HttpStatus.OK);
	}

}
