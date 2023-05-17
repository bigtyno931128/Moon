package com.bigtyno.moon.controller.response;

import com.bigtyno.moon.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public
class PostResponse {
    private Long id;
    private String title;
    private String content;
    private boolean status;
    private Integer star;
    private LocalDate deadLine;
    private UserResponse user;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.isStatus(),
                post.getStar(),
                post.getDeadLine(),
                UserResponse.fromUser(post.getUser()),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

}
