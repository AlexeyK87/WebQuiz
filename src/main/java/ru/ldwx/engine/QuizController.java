package ru.ldwx.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ldwx.engine.entity.Answer;
import ru.ldwx.engine.entity.Quiz;
import ru.ldwx.engine.entity.Result;
import ru.ldwx.engine.storage.QuizStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuizController {
    private final QuizStorage quizStorage;

    @Autowired
    public QuizController(QuizStorage quizStorage) {
        this.quizStorage = quizStorage;
    }

    @GetMapping("/api/quizzes")
    public List<Quiz> getAllQuiz() {
        return quizStorage.getAll();
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        Quiz quiz = quizStorage.getQuiz(id);
        if (quiz == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        return quiz;
    }

    @PostMapping(value = "/api/quizzes/{id}/solve", consumes = "application/json")
    public Result solve(@RequestBody Answer answer, @PathVariable int id) {
        Quiz quiz = quizStorage.getQuiz(id);
        if (quiz == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        if (answer.getAnswer().equals(quiz.getAnswer())) {
            return new Result(true);
        } else {
            return new Result(false);
        }
    }

    @PostMapping(value = "api/quizzes", consumes = "application/json")
    public Quiz saveQuiz(@Valid @RequestBody Quiz quiz) {
        String username = getCurrentUserName();
        quiz.setUserName(username);
        int id = quizStorage.saveQuiz(quiz);
        return quizStorage.getQuiz(id);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable int id) {
        Quiz quiz = quizStorage.getQuiz(id);
        if (quiz == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        if (quiz.getUserName().equals(getCurrentUserName())) {
            quizStorage.deleteQuiz(id);
            return new ResponseEntity<Quiz>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Quiz>(HttpStatus.FORBIDDEN);
    }

    private String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
