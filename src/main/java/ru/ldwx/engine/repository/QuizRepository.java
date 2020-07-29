package ru.ldwx.engine.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import ru.ldwx.engine.entity.Quiz;

@Controller
public interface QuizRepository extends CrudRepository<Quiz, Integer> {
}
