package com.bigtyno.moon.controller;

import com.bigtyno.moon.controller.request.UserJoinRequest;
import com.bigtyno.moon.controller.request.UserLoginRequest;
import com.bigtyno.moon.controller.response.AlarmResponse;
import com.bigtyno.moon.controller.response.Response;
import com.bigtyno.moon.controller.response.UserJoinResponse;
import com.bigtyno.moon.controller.response.UserLoginResponse;
import com.bigtyno.moon.model.User;
import com.bigtyno.moon.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user =  userService.join(request.getUserName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUserName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable , Authentication authentication) {
        return Response.success(userService.alarmList(authentication.getName(), pageable).map(AlarmResponse::fromAlarm));
    }
}
