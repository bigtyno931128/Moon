package com.bigtyno.moon.controller;

import com.bigtyno.moon.controller.request.UserJoinRequest;
import com.bigtyno.moon.exception.ErrorCode;
import com.bigtyno.moon.exception.MoonApplicationException;
import com.bigtyno.moon.model.User;
import com.bigtyno.moon.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void 회원가입() throws Exception {
        String userName = "userName";
        String password = "password";

        // TODO : Mocking
        when(userService.join(userName,password)).thenReturn(mock(User.class));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void 회원가입시_이미_회원가입된_userName으로_회원가입을_하는경우_에러반환() throws Exception {
        String userName = "userName";
        String password = "password";

        // TODO : Mocking
        when(userService.join(userName,password)).thenThrow(new MoonApplicationException(ErrorCode.DUPLICATED_USER_NAME));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isConflict());

    }

//    @Test
//    public void 회원가입시_패스워드가_형식에_맞지않은경우_에러반환() throws Exception {
//        String userName = "userName";
//        String password = "password";
//
//        // TODO : Mocking
//
//        mockMvc.perform(post("/api/v1/users/join")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
//                ).andDo(print())
//                .andExpect(status().is5xxServerError());
//
//    }

    @Test
    public void 로그인() throws Exception {
        String userName = "userName";
        String password = "password";

        // TODO : Mocking
        when(userService.login( userName, password)).thenReturn("test_token");

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void 로그인시_회원가입이_안된_userName_입력할경우_에러반환() throws Exception {
        String userName = "userName";
        String password = "password";

        // TODO : Mocking
        when(userService.login(userName, password)).thenThrow(new MoonApplicationException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void 로그인시_잘못된_패스워드를_입력할경우_에러반환() throws Exception {
        String userName = "userName";
        String password = "password";

        // TODO : Mocking
        when(userService.login(userName, password)).thenThrow(new MoonApplicationException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }

    /*
    알림 기능
     */
    @Test
    @WithMockUser
    void 알람기능() throws Exception {

        when(userService.alarmList(any(),any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/users/alarms")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 알람리스트_요청시_로그인하지_않은경우() throws Exception {

        when(userService.alarmList(any(),any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/users/alarms")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
