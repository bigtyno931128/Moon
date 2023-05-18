package com.bigtyno.moon.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AlarmType {
    NEW_COMMENT_ON_POST("새로운 댓글이 달렸습니다."),
            ;

    private final String alarmText;
}
