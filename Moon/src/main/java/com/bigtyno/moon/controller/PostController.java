package com.bigtyno.moon.controller;


import com.bigtyno.moon.controller.request.PostWriteRequest;
import com.bigtyno.moon.controller.response.Response;
import com.bigtyno.moon.model.User;
import com.bigtyno.moon.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> write(@RequestBody PostWriteRequest request , Authentication authentication) {
        postService.write(request.getTitle(), request.getContent(),request.getStar(),request.getDeadLine(), authentication.getName());
        return Response.success();
    }
}
