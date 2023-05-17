package com.bigtyno.moon.model;


import com.bigtyno.moon.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Long id ;

    private String title;

    private String content;

    private boolean status;

    private Integer star;

    private LocalDate deadLine;

    private User user;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Timestamp removedAt;


    public static Post fromEntity(PostEntity entity) {
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.isStatus(),
                entity.getStar(),
                entity.getDeadLine(),
                User.fromEntity(entity.getUser()),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getRemovedAt()
        );
    }
}
