package com.bigtyno.moon.controller.response;

import com.bigtyno.moon.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String comment;
    private Long userId;
    private String userName;
    private Long postId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

    public static CommentResponse fromComment(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUserId(),
                comment.getUserName(),
                comment.getPostId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getRemovedAt()
        );
    }
}