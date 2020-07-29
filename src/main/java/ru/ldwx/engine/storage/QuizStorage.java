package ru.ldwx.engine.storage;

import ru.ldwx.engine.entity.Quiz;

import java.util.List;

public interface QuizStorage {
    public Quiz getQuiz(int id);
    public void deleteQuiz(int id);
    public int saveQuiz(Quiz quiz);
    public List<Quiz> getAll();
}
