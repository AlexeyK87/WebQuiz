package ru.ldwx.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class Controller {
    private final Storage storage;

    @Autowired
    public Controller(Storage storage) {
        this.storage = storage;
    }

    @GetMapping("/api/quizzes")
    public List<Quiz> getAllQuiz() {
        return storage.getAll();
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        Quiz quiz = storage.getQuiz(id);
        if (quiz == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        return quiz;
    }

    @PostMapping(value = "/api/quizzes/{id}/solve", consumes = "application/json")
    public Result solve(@RequestParam Answer answer, @PathVariable int id) {
        Quiz quiz = storage.getQuiz(id);
        if (quiz == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        if (Arrays.equals(answer.getAnswer(), quiz.getAnswer())) {
            return new Result(true);
        } else {
            return new Result(false);
        }
    }

    @PostMapping(value = "api/quizzes", consumes = "application/json")
    public Quiz saveQuiz(@Valid @RequestBody Quiz quiz) {
        int id = storage.saveQuiz(quiz);
        return storage.getQuiz(id);
    }
}
