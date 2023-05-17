package com.bigtyno.moon.model;


import com.bigtyno.moon.model.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Comment {
    private Long id;
    private String comment;
    private Long userId;
    private String userName;
    private Long postId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

    public static Comment fromEntity(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getComment(),
                entity.getUser().getId(),
                entity.getUser().getUserName(),
                entity.getPost().getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getRemovedAt()
        );
    }
}
