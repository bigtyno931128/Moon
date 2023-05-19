package com.bigtyno.moon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"사용자가 존재 하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀번호가 틀립니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "token 이 유효하지않습니다."),
    OVER_DEADLINE(HttpStatus.INTERNAL_SERVER_ERROR, "마감일로 선정한 날짜가 지났습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "할일이 존재 하지 않습니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED,"인증되지 않습니다. 다시확인해주세요."),
    ALARM_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"알람서비스 연결실패 했습니다."),
    ;
    private final HttpStatus status;
    private final String message;

}
