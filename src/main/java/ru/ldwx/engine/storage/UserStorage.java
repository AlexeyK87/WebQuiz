package ru.ldwx.engine.storage;


import ru.ldwx.engine.entity.User;

public interface UserStorage {
    int save(User user);
    User findByEmail(String email);
}
