package ru.ldwx.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ldwx.engine.entity.User;
import ru.ldwx.engine.storage.UserStorage;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserStorage storage;

    @PostMapping(value = "/api/register", consumes = "application/json")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        if (storage.findByEmail(user.getEmail()) != null ||
                !isValidEmailAddress(user.getEmail())) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        int id = storage.save(user);
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
