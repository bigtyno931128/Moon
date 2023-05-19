package com.bigtyno.moon.controller;

import com.bigtyno.moon.controller.request.UserJoinRequest;
import com.bigtyno.moon.controller.request.UserLoginRequest;
import com.bigtyno.moon.controller.response.AlarmResponse;
import com.bigtyno.moon.controller.response.Response;
import com.bigtyno.moon.controller.response.UserJoinResponse;
import com.bigtyno.moon.controller.response.UserLoginResponse;
import com.bigtyno.moon.model.User;
import com.bigtyno.moon.service.AlarmService;
import com.bigtyno.moon.service.UserService;
import com.bigtyno.moon.util.ClassUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AlarmService alarmService;

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

    /*
    알림 기능 ( 알람의 경우 USER 에 대한 Alarm 이 발생하기에 같이 분류를 한 상태이다 . 따로 분류를 둬야 할지는 고민 .. )
     */
    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable , Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class);
        return Response.success(userService.alarmList(user.getId(), pageable).map(AlarmResponse::fromAlarm));
    }

    @GetMapping("/alarm/subscribe")
    public SseEmitter subscribe(Authentication authentication ) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class);
        return alarmService.connectAlarm(user.getId());
    }
}
