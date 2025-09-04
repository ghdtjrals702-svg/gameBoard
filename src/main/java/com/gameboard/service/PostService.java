package com.gameboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameboard.domain.Post;
import com.gameboard.domain.PostRepository;
import com.gameboard.domain.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;

    public PostDto addPost(PostDto postDto) {
        Post post = Post.builder()
                .postName(postDto.getPostName())
                .date(postDto.getDate())
                .name(postDto.getName())
                .view(postDto.getView())
                .contents(postDto.getContents())
                .img(postDto.getImg())
                .build();
        Post savedPost = postRepository.save(post);
        postDto.setId(savedPost.getId());
        return postDto;
    }

    public List<PostDto> getPost() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> list = new ArrayList<>();
        for (Post post : posts) {
            PostDto dto = objectMapper.convertValue(post, PostDto.class);
            list.add(dto);
        }
        return list;
    }
}

