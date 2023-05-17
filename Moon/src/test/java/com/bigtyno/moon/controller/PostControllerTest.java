package com.bigtyno.moon.controller;

import com.bigtyno.moon.controller.request.PostModifyRequest;
import com.bigtyno.moon.controller.request.PostWriteRequest;
import com.bigtyno.moon.exception.ErrorCode;
import com.bigtyno.moon.exception.MoonApplicationException;
import com.bigtyno.moon.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void 할일작성() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostWriteRequest("title", "content",2, LocalDate.now()))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 할일작성시_로그인한상태가_아니라면_에러발생() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostWriteRequest("title", "content",2, LocalDate.now()))))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_TOKEN.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 할일수정() throws Exception {
        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostModifyRequest("title", "content",true,2, LocalDate.now().plusDays(1)))))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithAnonymousUser
    void 할일수정시_로그인한상태가_아니라면_에러발생() throws Exception {
        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostModifyRequest("title", "content",true,2, LocalDate.now()))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser
    void 할일수정시_본인이_작성한_글이_아니라면_에러발생() throws Exception {
        doThrow(new MoonApplicationException(ErrorCode.INVALID_PERMISSION)).when(postService).modify(eq(1L), eq("title"), eq("content"),eq(true),eq(1),eq(LocalDate.now()),eq(1L));

        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostModifyRequest("title", "content",true,2, LocalDate.now()))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 할일수정시_수정하려는글이_없다면_에러발생() throws Exception {
        doThrow(new MoonApplicationException(ErrorCode.INVALID_PERMISSION)).when(postService).modify(eq(1L), eq("title"), eq("content"),eq(true),eq(1),eq(LocalDate.now()),eq(1L));
        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostModifyRequest("title", "content",true,2, LocalDate.now()))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @WithAnonymousUser
    void 할일삭제시_로그인한상태가_아니라면_에러발생() throws Exception {
        mockMvc.perform(delete("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_TOKEN.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 할일삭제시_본인이_작성한_글이_아니라면_에러발생() throws Exception {
        doThrow(new MoonApplicationException(ErrorCode.INVALID_PERMISSION)).when(postService).delete(any(), eq(1L));
        mockMvc.perform(delete("/api/v1/posts/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 할일삭제시_삭제하려는글이_없다면_에러발생() throws Exception {
        doThrow(new MoonApplicationException(ErrorCode.POST_NOT_FOUND)).when(postService).delete(any(), eq(1L));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getStatus().value()));
    }

}
