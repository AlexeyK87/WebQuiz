package ru.ldwx.engine.storage;


import ru.ldwx.engine.entity.User;

public interface UserStorage {
    public User get(int id);
    public int save(User user);
    public User findByEmail(String email);
}
