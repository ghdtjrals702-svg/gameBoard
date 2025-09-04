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
    private String date;
    private String postName;
    private Integer view;
    private String contents;
    private String img;


    public void updatePost(PostDto postDto) {
        this.name = postDto.name;
        this.date = postDto.date;
        this.postName = postDto.postName;
        this.view = postDto.view;
        this.contents = postDto.contents;
        this.img = postDto.img;

    }


}
