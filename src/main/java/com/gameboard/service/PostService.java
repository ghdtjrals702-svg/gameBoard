package com.gameboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameboard.domain.Post;
import com.gameboard.domain.PostRepository;
import com.gameboard.domain.dto.PostDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public PostDto addPost(PostDto postDto) {
        Post post = Post.builder()
                .postName(postDto.getPostName())
                .date(postDto.getDate())
                .view(postDto.getView())
                .contents(postDto.getContents())
                .img(postDto.getImg())
                .nickname(postDto.getNickname())
                .build();
        Post savedPost = postRepository.save(post);
        postDto.setId(savedPost.getId());
        return postDto;
    }

    // 한 개만 조회할 때에는 get ex) getData, getPost, getUser...
    // 여러 개 조회할 때에는 select ex) selectData, selectPost...
    public List<PostDto> selectPost() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> list = new ArrayList<>();
        for (Post post : posts) {
            PostDto dto = objectMapper.convertValue(post, PostDto.class);
            list.add(dto);
        }
        return list;
    }

    public PostDto getPost(Long id) throws Exception {
        Post post = postRepository.findById(id).orElseThrow(() -> new Exception("게시글 정보를 찾을 수 없습니다."));
        return objectMapper.convertValue(post, PostDto.class);
    }

    public Long deletePost(Long id) {
        postRepository.deleteById(id);
        return id;
    }

    public PostDto updatePost(PostDto postDto) {
        Post post =  postRepository.findById(postDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        post.updatePost(postDto);
        return postDto;
    }
}

