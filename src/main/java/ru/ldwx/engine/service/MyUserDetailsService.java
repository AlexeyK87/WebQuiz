package ru.ldwx.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ldwx.engine.entity.MyUserDetails;
import ru.ldwx.engine.entity.User;
import ru.ldwx.engine.storage.UserStorage;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserStorage storage;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = storage.findByEmail(username);
        return new MyUserDetails(user);
    }
}
