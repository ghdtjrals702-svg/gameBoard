package com.gameboard.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String name;
    private Integer date;
    private String postName;
    private Integer view;

    public void updatePost(PostDto postDto) {
        this.name = postDto.name;
        this.date = postDto.date;
        this.postName = postDto.postName;
        this.view = postDto.view;
    }


}
