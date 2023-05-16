package com.bigtyno.moon.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostWriteRequest  {

    private String title;
    private String content;
    private Integer star;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadLine;
}