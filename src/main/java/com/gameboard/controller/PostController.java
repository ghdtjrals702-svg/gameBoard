package com.gameboard.controller;

import com.gameboard.domain.dto.PostDto;
import com.gameboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public PostDto addPost(@RequestBody PostDto postDto) {
        return postService.addPost(postDto);
    }

    @GetMapping("/getPost")
    public List<PostDto> addPost() {
        List<PostDto> list = postService.getPost();
        return list;
    }

    @DeleteMapping("/post/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
