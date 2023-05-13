package com.bigtyno.moon.fixture;

import com.bigtyno.moon.model.entity.UserEntity;

// test 용 dummy
public class UserEntityFixture {
    public static UserEntity get(String userName, String password ) {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUserName(userName);
        user.setPassword(password);
        return user;
    }
}
