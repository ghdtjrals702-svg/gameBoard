package com.gameboard.controller;

import com.gameboard.domain.Post;
import com.gameboard.domain.dto.PostDto;
import com.gameboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/select")
    public List<PostDto> selectPost() {
        List<PostDto> list = postService.selectPost();
        return list;
    }

    @GetMapping("/get")
    public PostDto getPost(@RequestParam Long id) throws Exception {
        PostDto dto = postService.getPost(id);
        return dto;
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
