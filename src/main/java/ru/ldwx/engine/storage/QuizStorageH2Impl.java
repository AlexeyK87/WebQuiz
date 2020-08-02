package ru.ldwx.engine.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import ru.ldwx.engine.entity.Quiz;
import ru.ldwx.engine.repository.QuizRepository;

@Controller
public class QuizStorageH2Impl implements QuizStorage {
    private final QuizRepository repository;

    @Autowired
    public QuizStorageH2Impl(QuizRepository repository) {
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
    public void deleteQuiz(int id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Quiz> getAll(Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 10);
        return repository.findAll(paging);
    }
}
