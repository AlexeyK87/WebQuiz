package ru.ldwx.engine;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    private static List<Quiz> quizzes = new ArrayList<>();

    static {
        quizzes.add(new Quiz("The Java Logo",
                "What is depicted on the Java logo?",
                new String[]{"Robot","Tea leaf","Cup of coffee","Bug"}));
    }

    @GetMapping("/api/quiz")
    public Quiz getQuiz() {
        return quizzes.get(0);
    }

    @PostMapping("/api/quiz")
    public Result post(@RequestParam int answer) {
        if (answer == 2) {
            return new Result(true);
        } else {
            return new Result(false);
        }
    }
}
