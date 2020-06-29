package ru.ldwx.engine;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;

@Controller
public interface QuizRepository extends CrudRepository<Quiz, Integer> {
}
