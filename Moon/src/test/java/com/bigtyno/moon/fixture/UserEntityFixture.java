package com.bigtyno.moon.fixture;

import com.bigtyno.moon.model.UserRole;
import com.bigtyno.moon.model.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;


//test 용 dummy
public class UserEntityFixture {

    public static UserEntity get(String userName, String password) {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setUserName(userName);
        entity.setPassword(password);
        entity.setRole(UserRole.USER);
        entity.setCreatedAt(Timestamp.from(Instant.now()));
        return entity;
    }
}