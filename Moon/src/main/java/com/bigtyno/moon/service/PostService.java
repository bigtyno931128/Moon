package com.bigtyno.moon.service;

import com.bigtyno.moon.exception.ErrorCode;

import com.bigtyno.moon.exception.MoonApplicationException;
import com.bigtyno.moon.model.entity.PostEntity;
import com.bigtyno.moon.model.entity.UserEntity;
import com.bigtyno.moon.repository.PostEntityRepository;
import com.bigtyno.moon.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

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

}