package com.bigtyno.moon.fixture;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestInfoFixture {
    public static TestInfo get() {
        TestInfo info = new TestInfo();
        info.setPostId(1L);
        info.setUserId(1L);
        info.setUserName("name");
        info.setPassword("password");
        info.setTitle("title");
        info.setContent("content");
        info.setStatus(true);
        info.setStar(2);
        info.setDeadLine(LocalDate.now());
        return info;
    }

    @Data
    public static class TestInfo {
        private Long postId;
        private Long userId;
        private String userName;
        private String password;
        private String title;
        private String content;
        private boolean status;
        private Integer star;
        private LocalDate deadLine;
    }
}
