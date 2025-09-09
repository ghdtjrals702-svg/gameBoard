package com.gameboard.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name="comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Comment {
    //아이디, 유저네임, 닉네임, 날짜+시간, 댓글 내용,글 아이디
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String date;
    @Column(nullable = false)
    private String contents;
    @Column
    private String nickname;
    @Column
    private String username;

    @Column
    private Long post_id;

}









