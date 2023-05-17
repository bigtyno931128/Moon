package com.bigtyno.moon.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PostModifyRequest {
    private String title;
    private String content;
    private boolean status;
    private Integer star;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadLine;
}
