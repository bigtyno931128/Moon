package com.bigtyno.moon.fixture;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestInfoFixture {
    public static TestInfo get() {
        TestInfo info = new TestInfo();
        info.setPostId(1);
        info.setUserId(1);
        info.setUserName("name");
        info.setPassword("password");
        info.setTitle("title");
        info.setContent("content");
        info.setStar(2);
        info.setDeadLine(LocalDate.now());
        return info;
    }

    @Data
    public static class TestInfo {
        private Integer postId;
        private Integer userId;
        private String userName;
        private String password;
        private String title;
        private String content;
        private Integer star;
        private LocalDate deadLine;
    }
}
