package ru.ldwx.engine.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.ldwx.engine.entity.User;
import ru.ldwx.engine.repository.UserRepository;

@Controller
public class UserStorageH2Impl implements UserStorage {
    private final UserRepository repository;

    @Autowired
    public UserStorageH2Impl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public int save(User user) {
        User saved = repository.save(user);
        return saved.getId();
    }

    @Override
    public User findByEmail(String email) {
        return repository.findUserByEmail(email);
    }
}
