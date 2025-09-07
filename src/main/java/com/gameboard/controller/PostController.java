package com.gameboard.controller;

import com.gameboard.domain.dto.PostDto;
import com.gameboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostDto addPost(@RequestBody PostDto postDto) {
        return postService.addPost(postDto);
    }

    @GetMapping
    public List<PostDto> addPost() {
        List<PostDto> list = postService.getPost();
        return list;
    }

    @DeleteMapping("/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

    @PutMapping
    public PostDto updatePost(@RequestBody PostDto postDto) {
        return postService.updatePost(postDto);
    }
}
