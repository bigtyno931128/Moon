package com.bigtyno.moon.model.entity;

import java.sql.Timestamp;

public class StarEntity {


    private Long id ;

    private PostEntity post;

    private UserEntity user;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

}
