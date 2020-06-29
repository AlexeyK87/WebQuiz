package ru.ldwx.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StorageH2Impl implements Storage {
    private final QuizRepository repository;

    @Autowired
    public StorageH2Impl(QuizRepository repository) {
        this.repository = repository;
    }

    @Override
    public Quiz getQuiz(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public int saveQuiz(Quiz quiz) {
        Quiz savedQuiz = repository.save(quiz);
        return savedQuiz.getId();
    }

    @Override
    public List<Quiz> getAll() {
        List<Quiz> quizzes = new ArrayList<>();
        for (Quiz q : repository.findAll()) {
            quizzes.add(q);
        }
        return quizzes;
    }
}
