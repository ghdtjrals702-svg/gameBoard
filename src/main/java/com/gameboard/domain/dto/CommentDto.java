package com.gameboard.domain.dto;

import com.gameboard.domain.AppUser;
import com.gameboard.domain.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String date;
    private String username;
    private String contents;
    private String nickname;
    private int post_id;


}


