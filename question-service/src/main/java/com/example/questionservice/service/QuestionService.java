package com.example.questionservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.questionservice.model.Question;
import com.example.questionservice.model.QuestionInterface;
import com.example.questionservice.model.QuestionWrapper;
import com.example.questionservice.model.Response;
import com.example.questionservice.dao.QuestionDao;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    QuestionInterface questionInterface;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {

        try {
            return new ResponseEntity<>( questionDao.findByCategory(category),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("success",HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("failed",HttpStatus.NOT_MODIFIED);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Question> questionList=questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
        List<Integer> intList=new ArrayList<>();
        for(Question question:questionList){
            intList.add(question.getId());
        }
        return new ResponseEntity<>(intList,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(List<Integer> ids) {
        List<QuestionWrapper> questionWrapperList=new ArrayList<>();
        for(int id:ids){
            Optional<Question> op=questionDao.findById(id);
            QuestionWrapper qWrapper=new QuestionWrapper(op.get().getId(), op.get().getQuestionTitle(), op.get().getOption1(), 
            op.get().getOption2(), op.get().getOption3(), op.get().getOption4());
            questionWrapperList.add(qWrapper);
        }
        return new ResponseEntity<>(questionWrapperList,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScores(List<Response> responses) {
        int count =0;
        for(Response response : responses){
            Optional<Question> qsOp=questionDao.findById(response.getId());
            List<Integer> questionInteger=questionInterface.getQuestionIds(response.getQuizId()).getBody();
            for(int questionsInt:questionInteger){
                if(response.getId()==questionsInt && response.getResponse().equals(qsOp.get().getRightAnswer())){
                    count++;
            }
        }
        }
        return new ResponseEntity<>(count,HttpStatus.OK);
    }

}
