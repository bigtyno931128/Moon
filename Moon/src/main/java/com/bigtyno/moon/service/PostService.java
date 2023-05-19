package com.bigtyno.moon.service;

import com.bigtyno.moon.controller.response.PostResponse;
import com.bigtyno.moon.exception.ErrorCode;

import com.bigtyno.moon.exception.MoonApplicationException;
import com.bigtyno.moon.model.AlarmArgs;
import com.bigtyno.moon.model.AlarmType;
import com.bigtyno.moon.model.Comment;
import com.bigtyno.moon.model.Post;
import com.bigtyno.moon.model.entity.AlarmEntity;
import com.bigtyno.moon.model.entity.CommentEntity;
import com.bigtyno.moon.model.entity.PostEntity;
import com.bigtyno.moon.model.entity.UserEntity;
import com.bigtyno.moon.repository.AlarmRepository;
import com.bigtyno.moon.repository.CommentEntityRepository;
import com.bigtyno.moon.repository.PostEntityRepository;
import com.bigtyno.moon.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final CommentEntityRepository commentEntityRepository;
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    @Transactional
    public void write(String title, String content, Integer star, LocalDate deadLine, String userName ) {

        // 유저가 존재 하는지 .
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new MoonApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not founded", userName)));

        // 마감일< 현재 날짜 ( ex) 5월 15 일 인데 오늘날짜가 5월 16일인 경우 )
        if (deadLine.isBefore(LocalDate.now())) {
            throw new MoonApplicationException(ErrorCode.OVER_DEADLINE);
        }

        PostEntity saved = postEntityRepository.save(PostEntity.of(title, content ,star, deadLine, userEntity));
    }
    @Transactional
    public Post modify(Long postId, String title, String content, boolean status, Integer star, LocalDate deadLine, Long userId) {

        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() -> new MoonApplicationException(ErrorCode.POST_NOT_FOUND));
        if (!Objects.equals(postEntity.getUser().getId(), userId)) {
            throw new MoonApplicationException(ErrorCode.INVALID_PERMISSION);
        }

        postEntity.setTitle(title);
        postEntity.setContent(content);
        postEntity.setStatus(status);
        postEntity.setStar(star);
        postEntity.setDeadLine(deadLine);


        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(Long userId, Long postId) {
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new MoonApplicationException(ErrorCode.POST_NOT_FOUND));

        if (!Objects.equals(postEntity.getUser().getId(), userId)) {
            throw new MoonApplicationException(ErrorCode.INVALID_PERMISSION);
        }

        commentEntityRepository.deleteAllByPost(postEntity);
        postEntityRepository.delete(postEntity);
    }
    public Post detail(Long postId) {
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new MoonApplicationException(ErrorCode.POST_NOT_FOUND));
        return Post.fromEntity(postEntity);
    }

    public Page<Post> list(Pageable pageable) {
        return postEntityRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(Long userId, Pageable pageable) {
        return postEntityRepository.findAllByUserId(userId, pageable).map(Post::fromEntity);
    }


    /*
     댓글 기능
     */
    @Transactional
    public void comment(Long postId, String userName, String comment) {
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() ->
                new MoonApplicationException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));

        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new MoonApplicationException(ErrorCode.USER_NOT_FOUND, String.format("userName is %s", userName)));

        commentEntityRepository.save(CommentEntity.of(comment, postEntity, userEntity));

        AlarmEntity alarmEntity = alarmRepository.save(AlarmEntity.of(postEntity.getUser(), AlarmType.NEW_COMMENT_ON_POST,
                new AlarmArgs(userEntity.getId(),postId)));
        alarmService.send(postEntity.getUser().getId(), alarmEntity.getId());
    }

    public Page<Comment> getComments(Long postId, Pageable pageable) {
        PostEntity postEntity = postEntityRepository.findById(postId).orElseThrow(() -> new MoonApplicationException(ErrorCode.POST_NOT_FOUND, String.format("postId is %d", postId)));
        return commentEntityRepository.findAllByPost(postEntity, pageable).map(Comment::fromEntity);
    }


}
