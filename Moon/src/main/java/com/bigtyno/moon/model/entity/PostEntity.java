package com.bigtyno.moon.model.entity;

import java.sql.Timestamp;

public class PostEntity {

    private Long id ;

    private String title;


    private String content;

    private boolean status;

    private Timestamp deadline;


    private UserEntity user;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

}
