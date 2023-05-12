package com.bigtyno.moon.model.entity;

import com.bigtyno.moon.model.UserRole;

import java.sql.Timestamp;

public class UserEntity {
    private Long id;
    private String userName;
    private String password;
    private UserRole role = UserRole.USER;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

}
