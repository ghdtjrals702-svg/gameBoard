package com.gameboard.service;

import com.gameboard.domain.Post;
import com.gameboard.domain.PostRepository;
import com.gameboard.domain.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    public PostDto addPost(PostDto postDto) {
        Post post = Post.builder()
                .postName(postDto.getPostName())
                .date(postDto.getDate())
                .name(postDto.getName())
                .view(postDto.getView())
                .build();
        Post savedPost = postRepository.save(post);
        postDto.setId(savedPost.getId());

        return postDto;
        }

    }

