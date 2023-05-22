package com.bigtyno.moon.controller;

import com.bigtyno.moon.controller.response.CommentResponse;
import com.bigtyno.moon.controller.response.PostResponse;
import com.bigtyno.moon.service.PaginationService;
import com.bigtyno.moon.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostViewController {

    private final PostService postService;
    private final PaginationService paginationService;

    // 할일 목록
    @GetMapping("/posts")
    public String posts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        Page<PostResponse> posts = postService.list(pageable).map(PostResponse::fromPost);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), posts.getTotalPages());

        map.addAttribute("posts", posts);
        map.addAttribute("paginationBarNumbers", barNumbers);

        return "posts/index";
    }

    // 할일 ( 상세 페이지 )
    @GetMapping("/posts/{postId}")
    public String post(@PathVariable Long postId, ModelMap map ,
                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PostResponse post = PostResponse.fromPost(postService.detail(postId));
        map.addAttribute("post", post);
        map.addAttribute("postComments",postService.getComments(postId, pageable).map(CommentResponse::fromComment));

        return "posts/detail";
    }
}
