package ru.ldwx.engine;

import java.util.ArrayList;
import java.util.List;

public class StorageInMemoryImpl implements Storage {
    private final List<Quiz> quizzes = new ArrayList<>();
    private int counter = 0;

    @Override
    public Quiz getQuiz(int id) {
        if (quizzes.size() > id) {
            return quizzes.get(id);
        }
        return null;
    }

    @Override
    public int saveQuiz(Quiz quiz) {
        quiz.setId(counter);
        quizzes.add(quiz);
        return counter++;
    }

    @Override
    public List<Quiz> getAll() {
        return new ArrayList<>(quizzes);
    }
}
