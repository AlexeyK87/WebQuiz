package ru.ldwx.engine.storage;

import ru.ldwx.engine.entity.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizStorageInMemoryImpl implements QuizStorage {
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

    @Override
    public void deleteQuiz(int id) {
        quizzes.remove(id);
    }
}
