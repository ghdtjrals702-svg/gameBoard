package com.gameboard.service;

import com.gameboard.domain.*;
import com.gameboard.domain.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    //댓글 추가
    public CommentDto addComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .date(commentDto.getDate())
                .username(commentDto.getUsername())
                .contents(commentDto.getContents())
                .nickname(commentDto.getNickname())
                .build();
        Comment savedComment = commentRepository.save(comment);
        commentDto.setId(savedComment.getId());

        return commentDto;
    }


}