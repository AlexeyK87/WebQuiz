package ru.ldwx.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ldwx.engine.entity.Answer;
import ru.ldwx.engine.entity.Completion;
import ru.ldwx.engine.entity.Quiz;
import ru.ldwx.engine.entity.Result;
import ru.ldwx.engine.storage.CompletionStorage;
import ru.ldwx.engine.storage.QuizStorage;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
public class QuizController {
    private final QuizStorage quizStorage;
    private final CompletionStorage completionStorage;

    @Autowired
    public QuizController(QuizStorage quizStorage, CompletionStorage completionStorage) {
        this.quizStorage = quizStorage;
        this.completionStorage = completionStorage;
    }

    @GetMapping("/api/quizzes")
    public Page<Quiz> getAllQuiz(@RequestParam(defaultValue = "0") Integer page) {
        return quizStorage.getAll(page);
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
            String userName = getCurrentUserName();
            LocalDateTime dateTime = LocalDateTime.now();
            Completion completion = new Completion(userName, id, dateTime);
            completionStorage.save(completion);
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

    @GetMapping("/api/quizzes/completed")
    public Page<Completion> getCompleted(@RequestParam(defaultValue = "0") Integer page) {
        String name = getCurrentUserName();
        return completionStorage.getAllByUserName(page, name);
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
