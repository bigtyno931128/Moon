package com.bigtyno.moon.model.entity;

import java.sql.Timestamp;

public class CommentEntity {

    private Long id ;

    private String comment;

    private UserEntity user;

    private PostEntity post;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

}
