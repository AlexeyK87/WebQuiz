package ru.ldwx.engine.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ldwx.engine.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>  {
    User findUserByEmail(String email);
}
