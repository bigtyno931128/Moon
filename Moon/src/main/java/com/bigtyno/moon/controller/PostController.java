package com.bigtyno.moon.controller;


import com.bigtyno.moon.controller.request.PostCommentRequest;
import com.bigtyno.moon.controller.request.PostModifyRequest;
import com.bigtyno.moon.controller.request.PostWriteRequest;
import com.bigtyno.moon.controller.response.CommentResponse;
import com.bigtyno.moon.controller.response.PostResponse;
import com.bigtyno.moon.controller.response.Response;
import com.bigtyno.moon.model.User;
import com.bigtyno.moon.service.PostService;
import com.bigtyno.moon.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable Long postId, @RequestBody PostModifyRequest request, Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class);
        return Response.success(
                PostResponse.fromPost(
                        postService.modify(postId, request.getTitle(), request.getContent(),request.isStatus(),request.getStar(),request.getDeadLine(),user.getId())));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable Long postId, Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class);
        postService.delete(user.getId(), postId);
        return Response.success();
    }
    @GetMapping("/{postId}")
    public Response<PostResponse> detail(@PathVariable Long postId) {
        return Response.success(
                PostResponse.fromPost(postService.detail(postId)));
    }
    @GetMapping
    public Response<Page<PostResponse>> list(Pageable pageable) {
        return Response.success(postService.list(pageable).map(PostResponse::fromPost));
    }

    @GetMapping("/my")
    public Response<Page<PostResponse>> myPosts(Pageable pageable, Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class);
        return Response.success(postService.my(user.getId(), pageable).map(PostResponse::fromPost));
    }


    @PostMapping("/{postId}/comments")
    public Response<Void> comment(@PathVariable Long postId, @RequestBody PostCommentRequest request, Authentication authentication) {
        postService.comment(postId, authentication.getName(), request.getComment());
        return Response.success();
    }

    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> getComments(Pageable pageable, @PathVariable Long postId) {
        return Response.success(postService.getComments(postId, pageable).map(CommentResponse::fromComment));
    }

}
