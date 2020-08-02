package ru.ldwx.engine.storage;

import org.springframework.data.domain.Page;
import ru.ldwx.engine.entity.Quiz;

import java.util.List;

public interface QuizStorage {
    Quiz getQuiz(int id);
    void deleteQuiz(int id);
    int saveQuiz(Quiz quiz);
    Page<Quiz> getAll(Integer pageNo);
}
