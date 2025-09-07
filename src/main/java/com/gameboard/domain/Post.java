package com.gameboard.domain;

import com.gameboard.domain.dto.PostDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name="post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String postName;
    private Integer view;
    private String contents;
    private String img;
    private String nickname;


    public void updatePost(PostDto postDto) {
        this.date = postDto.getDate();
        this.postName = postDto.getPostName();
        this.view = postDto.getView();
        this.contents = postDto.getContents();
        this.img = postDto.getImg();
        this.nickname = postDto.getNickname();

    }
}
//{
//        "name": "이름",
//        "date": "2025-01-01",
//        "postName": "아무거나",
//        "view": 0,
//        "contents":"자고 싶어",
//        "img": "사진"
//
//        }
