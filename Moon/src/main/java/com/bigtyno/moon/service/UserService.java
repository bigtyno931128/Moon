package com.bigtyno.moon.service;

import com.bigtyno.moon.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User join() {
        return new User();
    }

}
