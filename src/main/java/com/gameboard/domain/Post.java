package com.gameboard.domain;

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
    private String name;
    private String date;
    private String postName;
    private Integer view;
    private String contents;
    private String img;


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
