package ru.ldwx.engine;

import java.util.List;

public interface Storage {
    public Quiz getQuiz(int id);
    public int saveQuiz(Quiz quiz);
    public List<Quiz> getAll();
}
