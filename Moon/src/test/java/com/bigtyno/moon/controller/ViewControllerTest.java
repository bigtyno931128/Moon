package com.bigtyno.moon.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러")
@WebMvcTest(ViewController.class)
class ViewControllerTest {
    private final MockMvc mvc;
    public ViewControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    public void 일정목록_페이지() throws Exception {

        mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("posts/index"))
                .andExpect(model().attributeExists("posts"));

    }

    @Test
    public void 일정상세_페이지() throws Exception {

        mvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("posts/1"))
                .andExpect(view().name("posts/detail"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("postComments"));

    }

    @Test
    public void 할일_검색_전용페이지() throws Exception {

        mvc.perform(get("/posts/Search"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/search"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));

    }

}