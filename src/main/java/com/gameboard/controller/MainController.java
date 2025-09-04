package com.gameboard.controller;

import com.gameboard.domain.dto.PostDto;
import com.gameboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final PostService postService;
    @PostMapping("/postPage")
    public PostDto addPost(@RequestBody PostDto postDto) {
        return postService.addPost(postDto);

    }
}
